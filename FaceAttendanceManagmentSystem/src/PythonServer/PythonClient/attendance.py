import cv2
import numpy as np
import os
import pickle
import time
import mysql.connector
from datetime import datetime, date
from insightface.app import FaceAnalysis
import warnings
warnings.filterwarnings("ignore", category=UserWarning)

# ====== CONFIG ======
FRAME_SKIP = 2
COOLDOWN_SECONDS = 5
FACE_SIMILARITY_THRESHOLD = 0.45
RESIZE_WIDTH = 800
RESIZE_HEIGHT = 600
DATABASE_PATH = "face_embeddings.pkl"

# ====== Load Face Embeddings ======
if os.path.exists(DATABASE_PATH):
    with open(DATABASE_PATH, "rb") as f:
        database = pickle.load(f)
else:
    database = {}
    print("No face database found. Please register faces first.")

# MySQL config
DB_HOST = "localhost"
DB_USER = "root"
DB_PASSWORD = "Riddhiman@108"
DB_NAME = "faceattendancedb"

# ====== Load Face Analysis ======
face_app = FaceAnalysis(name="buffalo_sc", providers=['CPUExecutionProvider'])
face_app.prepare(ctx_id=0)

# ====== Cooldown Tracker ======
last_attendance = {}

# ====== Cosine Similarity ======
def cosine_similarity(a, b):
    return np.dot(a, b) / (np.linalg.norm(a) * np.linalg.norm(b))

# ====== Mark Attendance ======
def mark_attendance(enrollment_no):
    now = datetime.now()
    today = date.today()

    try:
        conn = mysql.connector.connect(
            host=DB_HOST,
            user=DB_USER,
            password=DB_PASSWORD,
            database=DB_NAME
        )
        cursor = conn.cursor()

        cursor.execute(
            "SELECT id FROM attendancedata WHERE enrollment_no = %s AND date = %s",
            (enrollment_no, today)
        )
        result = cursor.fetchone()

        if result:
            print(f"{enrollment_no} already marked present on {today}")
        else:
            cursor.execute(
                "INSERT INTO attendancedata (enrollment_no, status, checkin, date) VALUES (%s, %s, %s, %s)",
                (enrollment_no, "Present", now, today)
            )
            conn.commit()
            print(f"Marked {enrollment_no} present at {now.strftime('%H:%M:%S')}")

    except mysql.connector.Error as err:
        print(f"[DB ERROR] {err}")

    finally:
        cursor.close()
        conn.close()

# ====== Main Loop ======
cap = cv2.VideoCapture(0)
frame_count = 0

print("Starting AI Face Attendance System...")

while True:
    ret, frame = cap.read()
    if not ret:
        break

    frame_count += 1
    if frame_count % FRAME_SKIP != 0:
        continue

    frame_resized = cv2.resize(frame, (RESIZE_WIDTH, RESIZE_HEIGHT))
    rgb = cv2.cvtColor(frame_resized, cv2.COLOR_BGR2RGB)
    faces = face_app.get(rgb)

    for face in faces:
        box = face.bbox.astype(int)
        embedding = face.embedding

        name = "Unknown"
        best_score = 0.0

        for person, db_emb in database.items():
            score = cosine_similarity(embedding, db_emb)
            if score > best_score:
                best_score = score
                name = person if score > FACE_SIMILARITY_THRESHOLD else "Unknown"

        if name != "Unknown":
            now = time.time()
            if name not in last_attendance or (now - last_attendance[name]) > COOLDOWN_SECONDS:
                last_attendance[name] = now
                mark_attendance(name)

        # Draw bounding box
        cv2.rectangle(frame_resized, (box[0], box[1]), (box[2], box[3]), (0, 255, 0), 2)
        cv2.putText(frame_resized, f"{name} ({best_score:.2f})", (box[0], box[1] - 10),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.6, (255, 255, 0), 2)

    cv2.imshow("AI Face Attendance", frame_resized)

    if cv2.waitKey(1) & 0xFF == ord(' '):
        break

cap.release()
cv2.destroyAllWindows()
