import os
import cv2
import pickle
from insightface.app import FaceAnalysis

# Config
FOLDER = "registered_faces"
DATABASE_PATH = "face_embeddings.pkl"

# Load InsightFace
app = FaceAnalysis(name="buffalo_sc", providers=['CPUExecutionProvider'])
app.prepare(ctx_id=0)

# Database to store embeddings
database = {}

# Loop through all images in the folder
for filename in os.listdir(FOLDER):
    if filename.lower().endswith((".jpg", ".jpeg", ".png")):
        enrollment_no = os.path.splitext(filename)[0]
        image_path = os.path.join(FOLDER, filename)

        img = cv2.imread(image_path)
        if img is None:
            print(f"[SKIP] Could not read image: {filename}")
            continue

        rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        faces = app.get(rgb)

        if faces:
            emb = faces[0].embedding
            database[enrollment_no] = emb
            print(f"[OK] Registered {enrollment_no}")
        else:
            print(f"[FAIL] No face detected in {filename}")

# Save to pickle
with open(DATABASE_PATH, "wb") as f:
    pickle.dump(database, f)

print(" All valid face images have been converted to embeddings.")
