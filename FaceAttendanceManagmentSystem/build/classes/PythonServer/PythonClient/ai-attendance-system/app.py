from flask import Flask, render_template, request, redirect, url_for, flash, jsonify
import base64
import os
import uuid
import pickle
import re
import mysql.connector
from datetime import datetime
import cv2
import numpy as np
from insightface.app import FaceAnalysis
import json
import time
import threading
import warnings

# ==============================================
# Environment Configuration
# ==============================================

# Disable all unnecessary warnings
warnings.filterwarnings("ignore", category=UserWarning)
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'  # Disable TensorFlow logging
os.environ['NO_ALBUMENTATIONS_UPDATE'] = '1'  # Disable Albumentations update checks
os.environ['CUDA_VISIBLE_DEVICES'] = '-1'  # Force CPU-only mode

def configure_environment():
    """Configure environment for optimal CPU performance"""
    os.environ['OMP_NUM_THREADS'] = '4'
    os.environ['KMP_BLOCKTIME'] = '1'
    os.environ['KMP_AFFINITY'] = 'granularity=fine,compact,1,0'
    os.environ['INSIGHTFACE_NO_GPU'] = '1'

configure_environment()

# ==============================================
# Flask Application Setup
# ==============================================

app = Flask(__name__)
app.secret_key = 'your-secret-key-here'

# MySQL Configuration
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = 'Riddhiman@108'
app.config['MYSQL_DB'] = 'student_db'
app.config['UPLOAD_FOLDER'] = 'static/uploads'
os.makedirs(app.config['UPLOAD_FOLDER'], exist_ok=True)

# ==============================================
# Face Analysis Initialization
# ==============================================

try:
    face_app = FaceAnalysis(
        name='buffalo_l', 
        providers=['CPUExecutionProvider'],
        allowed_modules=['detection', 'recognition']
    )
    face_app.prepare(ctx_id=-1)  # Force CPU usage
    print(" Face analysis model loaded in CPU-only mode")
except Exception as e:
    print(f" Failed to initialize face analysis: {str(e)}")
    raise

# ==============================================
# Database Connection Handling
# ==============================================

def get_db_connection():
    """Create and return a new database connection"""
    return mysql.connector.connect(
        host=app.config['MYSQL_HOST'],
        user=app.config['MYSQL_USER'],
        password=app.config['MYSQL_PASSWORD'],
        database=app.config['MYSQL_DB']
    )

# ==============================================
# Application Routes
# ==============================================

extraction_progress = {}
progress_lock = threading.Lock()

@app.route('/')
def home():
    return redirect(url_for('dashboard'))

@app.route('/dashboard')
def dashboard():
    return render_template('dashboard.html')

@app.route('/register')
def register():
    return render_template('index.html')

@app.route('/attendance', methods=['GET', 'POST'])
def attendance():
    if request.method == 'POST':
        try:
            data = request.json
            image_data = data.get('image_data')
            realtime = data.get('realtime', True)

            if not image_data or not image_data.startswith('data:image'):
                return jsonify({'status': 'error', 'message': 'Invalid image data'})

            # Decode image to NumPy array
            header, encoded = image_data.split(",", 1)
            image_bytes = base64.b64decode(encoded)
            nparr = np.frombuffer(image_bytes, np.uint8)
            img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)

            # Setup fast face model
            FACE_SIMILARITY_THRESHOLD = 0.45
            face_app_local = FaceAnalysis(name="buffalo_sc", providers=['CPUExecutionProvider'])
            face_app_local.prepare(ctx_id=0)

            # Detect faces
            rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
            faces = face_app_local.get(rgb)
            if not faces:
                return jsonify({'status': 'error', 'message': 'No face detected'})

            embedding = faces[0].embedding
            best_score = 0.0
            best_match = None

            # Fetch all students and compare embeddings
            conn = get_db_connection()
            cursor = conn.cursor(dictionary=True)
            cursor.execute("SELECT id, name, enrollment_no, face_embedding FROM students")
            students = cursor.fetchall()

            for student in students:
                try:
                    stored_embedding = np.array(json.loads(student['face_embedding']))
                    score = np.dot(embedding, stored_embedding) / (
                        np.linalg.norm(embedding) * np.linalg.norm(stored_embedding)
                    )
                    if score > best_score:
                        best_score = score
                        best_match = student if score > FACE_SIMILARITY_THRESHOLD else None
                except Exception as e:
                    continue  # Skip bad/malformed embeddings

            if not best_match:
                cursor.close()
                conn.close()
                return jsonify({'status': 'error', 'message': 'No matching student found'})

            # Cooldown check (if realtime mode)
            if realtime:
                cursor.execute("""
                    SELECT 1 FROM attendance 
                    WHERE student_id = %s 
                    AND timestamp > NOW() - INTERVAL 5 MINUTE
                    LIMIT 1
                """, (best_match['id'],))
                recent = cursor.fetchone()
                if recent:
                    cursor.close()
                    conn.close()
                    return jsonify({'status': 'error', 'message': f"Attendance already recorded for {best_match['name']} in last 5 minutes"})

            # Insert attendance
            attendance_id = str(uuid.uuid4())
            cursor.execute("""
                INSERT INTO attendance (id, student_id, timestamp, similarity)
                VALUES (%s, %s, %s, %s)
            """, (attendance_id, best_match['id'], datetime.now(), float(best_score)))

            conn.commit()
            cursor.close()
            conn.close()

            return jsonify({
                'status': 'success',
                'student': {
                    'name': best_match['name'],
                    'enrollment_no': best_match['enrollment_no']
                },
                'similarity': float(best_score)
            })

        except Exception as e:
            if 'conn' in locals():
                conn.close()
            return jsonify({'status': 'error', 'message': str(e)})

    return render_template('attendance.html')

@app.route('/api/stats')
def get_stats():
    try:
        conn = get_db_connection()
        cursor = conn.cursor()
        
        # Get total students
        cursor.execute("SELECT COUNT(*) FROM students")
        total_students = cursor.fetchone()[0]
        
        # Get today's attendance
        today = datetime.now().strftime('%Y-%m-%d')
        cursor.execute("SELECT COUNT(DISTINCT student_id) FROM attendance WHERE DATE(timestamp) = %s", (today,))
        today_attendance = cursor.fetchone()[0]
        
        cursor.close()
        conn.close()
        
        return jsonify({
            'total_students': total_students,
            'today_attendance': today_attendance
        })
    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})

@app.route('/api/attendance')
def get_attendance():
    try:
        date = request.args.get('date')
        if not date:
            date = datetime.now().strftime('%Y-%m-%d')
        
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        
        cursor.execute("""
            SELECT a.timestamp, s.name as student_name, s.enrollment_no, a.similarity
            FROM attendance a
            JOIN students s ON a.student_id = s.id
            WHERE DATE(a.timestamp) = %s
            ORDER BY a.timestamp DESC
            LIMIT 20
        """, (date,))
        
        attendance = cursor.fetchall()
        cursor.close()
        conn.close()
        
        return jsonify({
            'status': 'success',
            'attendance': attendance
        })
    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})

@app.route('/extract_face', methods=['POST'])
def extract_face():
    try:
        data = request.json
        image_data = data.get('image_data')
        
        if not image_data or not image_data.startswith('data:image'):
            return jsonify({'status': 'error', 'message': 'Invalid image data'})
        
        session_id = str(uuid.uuid4())
        
        with progress_lock:
            extraction_progress[session_id] = {
                'status': 'processing',
                'progress': 0,
                'embedding': None,
                'image_path': None
            }
        
        # Start face extraction in background thread
        thread = threading.Thread(
            target=process_face_extraction,
            args=(session_id, image_data)
        )
        thread.start()
        
        return jsonify({'status': 'processing', 'session_id': session_id})
    
    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})

def process_face_extraction(session_id, image_data):
    """Background task for face extraction"""
    try:
        # Save image
        header, encoded = image_data.split(",", 1)
        image_bytes = base64.b64decode(encoded)
        filename = f"face_{datetime.now().strftime('%Y%m%d%H%M%S')}.png"
        filepath = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        
        with open(filepath, 'wb') as f:
            f.write(image_bytes)
        
        # Simulate progress updates
        for progress in range(10, 101, 10):
            time.sleep(0.3)  # Simulate processing time
            with progress_lock:
                extraction_progress[session_id]['progress'] = progress
            
            if progress == 100:
                # Final processing
                img = cv2.imread(filepath)
                faces = face_app.get(img)
                
                with progress_lock:
                    if len(faces) > 0:
                        extraction_progress[session_id].update({
                            'status': 'completed',
                            'embedding': faces[0].embedding.tolist(),
                            'image_path': filename
                        })
                    else:
                        extraction_progress[session_id].update({
                            'status': 'failed',
                            'message': 'No face detected'
                        })
                        os.remove(filepath)
    
    except Exception as e:
        with progress_lock:
            extraction_progress[session_id].update({
                'status': 'error',
                'message': str(e)
            })
        if 'filepath' in locals() and os.path.exists(filepath):
            os.remove(filepath)

@app.route('/progress/<session_id>')
def get_progress(session_id):
    with progress_lock:
        progress = extraction_progress.get(session_id, {'status': 'unknown'})
    return jsonify(progress)

@app.route('/save_student', methods=['POST'])
def save_student():
    try:
        data = request.json
        session_id = data.get('session_id')
        
        with progress_lock:
            progress = extraction_progress.get(session_id)
            
            if not progress:
                return jsonify({'status': 'error', 'message': 'Invalid session ID'})
            
            if progress['status'] != 'completed':
                return jsonify({'status': 'error', 'message': 'Face extraction not complete'})
            
            # Validate required fields
            required_fields = ['name', 'gender', 'email', 'enrollment_no', 
                             'contact', 'course', 'department', 'address', 
                             'state', 'country']
            
            for field in required_fields:
                if not data.get(field):
                    return jsonify({'status': 'error', 'message': f'Missing {field}'})
            
            # Prepare student data
            student_data = {
                'id': str(uuid.uuid4()),
                'name': data['name'].strip().upper(),
                'gender': data['gender'].upper(),
                'email': data['email'].strip().lower(),
                'enrollment_no': data['enrollment_no'].strip().upper(),
                'contact': data['contact'].strip(),
                'course': data['course'].upper(),
                'department': data['department'].strip().upper(),
                'address': data['address'].strip().upper(),
                'state': data['state'].strip().upper(),
                'country': data['country'].strip().upper(),
                'image_path': progress['image_path'],
                'face_embedding': json.dumps(progress['embedding']),
                'created_at': datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            }
            
            # Save to database
            conn = get_db_connection()
            cursor = conn.cursor(dictionary=True)
            
            cursor.execute("""
                INSERT INTO students 
                (id, name, gender, email, enrollment_no, contact, course, department, 
                 address, state, country, image_path, face_embedding, created_at)
                VALUES (%(id)s, %(name)s, %(gender)s, %(email)s, %(enrollment_no)s, 
                        %(contact)s, %(course)s, %(department)s, %(address)s, 
                        %(state)s, %(country)s, %(image_path)s, %(face_embedding)s, 
                        %(created_at)s)
            """, student_data)
            
            conn.commit()
            student_id = cursor.lastrowid
            cursor.close()
            conn.close()
            
            # Clean up progress tracking
            del extraction_progress[session_id]
            
            return jsonify({
                'status': 'success', 
                'student_id': student_data['id']
            })
    
    except mysql.connector.Error as err:
        return jsonify({'status': 'error', 'message': f"Database error: {err}"})
    
    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)})

@app.route('/success/<student_id>')
def success(student_id):
    try:
        conn = get_db_connection()
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT * FROM students WHERE id = %s", (student_id,))
        student = cursor.fetchone()
        cursor.close()
        conn.close()
        
        if not student:
            flash('Student not found', 'error')
            return redirect(url_for('register'))
        
        return render_template('success.html', student=student)
    
    except Exception as e:
        flash(f'Error retrieving student: {str(e)}', 'error')
        return redirect(url_for('register'))

if __name__ == '__main__':
    app.run(debug=True, threaded=True)