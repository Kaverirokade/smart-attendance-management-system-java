import cv2
import sys
import os
import time
import pickle
from insightface.app import FaceAnalysis
import warnings
warnings.filterwarnings("ignore", category=UserWarning)

# Get enrollment number from command-line argument
if len(sys.argv) < 2:
    print("No enrollment number passed.")
    sys.exit(1)

enrollment_no = sys.argv[1]
save_path = f"src/PythonServer/PythonClient/registered_faces/{enrollment_no}.jpg"
os.makedirs(os.path.dirname(save_path), exist_ok=True)

# Load InsightFace
face_app = FaceAnalysis(name="buffalo_sc", providers=['CPUExecutionProvider'])
face_app.prepare(ctx_id=0)

# Load or initialize database
db_path = "face_embeddings.pkl"
if os.path.exists(db_path):
    with open(db_path, "rb") as f:
        database = pickle.load(f)
else:
    database = {}

# Open camera
cap = cv2.VideoCapture(0)
print("Press SPACE to capture face...")

while True:
    ret, frame = cap.read()
    if not ret:
        break
    cv2.imshow("Camera Registration", frame)
    key = cv2.waitKey(1) & 0xFF
    if key == 32:  # SPACE key
        # Save image
        cv2.imwrite(save_path, frame)
        print(f"[OK] Saved to {save_path}")

        # Detect face
        rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        faces = face_app.get(rgb)

        if faces:
            embedding = faces[0].embedding
            database[enrollment_no] = embedding

            # Save embedding to pickle
            with open(db_path, "wb") as f:
                pickle.dump(database, f)

            print(f"Face registered for {enrollment_no}")
        else:
            print("No face detected. Please try again.")
        
        # Show the image
        img = cv2.imread(save_path)
        win_name = "Registered Face"
        cv2.namedWindow(win_name, cv2.WINDOW_NORMAL)

        screen_res = (1920, 1080)
        try:
            import ctypes
            user32 = ctypes.windll.user32
            screen_res = user32.GetSystemMetrics(0), user32.GetSystemMetrics(1)
        except:
            pass

        max_dim = 400
        scale = min(max_dim / img.shape[1], max_dim / img.shape[0])
        resized = cv2.resize(img, (int(img.shape[1]*scale), int(img.shape[0]*scale)))

        x = screen_res[0]//2 - resized.shape[1]//2
        y = screen_res[1]//2 - resized.shape[0]//2
        cv2.moveWindow(win_name, x, y)
        cv2.imshow(win_name, resized)
        cv2.waitKey(3000)
        break

    elif key == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
