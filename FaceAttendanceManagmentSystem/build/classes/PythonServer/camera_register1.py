import os
import cv2
import pickle
import numpy as np
from insightface.app import FaceAnalysis

# === CONFIG ===
DATABASE_PATH = "face_embeddings.pkl"
SAVE_FOLDER = "registered_faces"
os.makedirs(SAVE_FOLDER, exist_ok=True)

# === Load or initialize database ===
if os.path.exists(DATABASE_PATH):
    with open(DATABASE_PATH, "rb") as f:
        database = pickle.load(f)
else:
    database = {}

# === Initialize FaceAnalysis ===
app = FaceAnalysis(name="buffalo_sc", providers=['CPUExecutionProvider'])
app.prepare(ctx_id=0)

# === Input enrollment number ===
enrollment_no = input("Enter Enrollment No: ").strip()

# === Open camera and capture ===
cap = cv2.VideoCapture(0)
print("Press SPACE to capture face...")

while True:
    ret, frame = cap.read()
    if not ret:
        continue

    cv2.imshow("Register Face", frame)
    key = cv2.waitKey(1)
    if key == ord(' '):
        # Save full frame for backup
        face_img_path = os.path.join(SAVE_FOLDER, f"{enrollment_no}.jpg")
        cv2.imwrite(face_img_path, frame)
        print(f"Saved image: {face_img_path}")

        # Detect face and extract embedding
        faces = app.get(cv2.cvtColor(frame, cv2.COLOR_BGR2RGB))
        if faces:
            emb = faces[0].embedding
            database[enrollment_no] = emb
            print(f"Face registered for {enrollment_no}")
        else:
            print("No face detected.")
        break

cap.release()
cv2.destroyAllWindows()

# === Save updated database ===
with open(DATABASE_PATH, "wb") as f:
    pickle.dump(database, f)
print("Database updated.")
