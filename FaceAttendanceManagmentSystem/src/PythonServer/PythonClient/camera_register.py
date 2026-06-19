import cv2
import sys
import os
import time
import pickle
import tkinter as tk
from PIL import Image, ImageTk
from insightface.app import FaceAnalysis
import numpy as np
import cv2
import sys
import os
import pickle
import tkinter as tk
from PIL import Image, ImageTk
from insightface.app import FaceAnalysis
import numpy as np
import warnings
warnings.filterwarnings("ignore", category=UserWarning)

# Enrollment number check
if len(sys.argv) < 2:
    print(" No enrollment number passed.")
    sys.exit(1)
enrollment_no = sys.argv[1]

# Paths
save_folder = "src/PythonServer/PythonClient/registered_faces"
save_path = os.path.join(save_folder, f"{enrollment_no}.jpg")
os.makedirs(save_folder, exist_ok=True)

# Load/init database
db_path = "face_embeddings.pkl"
if os.path.exists(db_path):
    with open(db_path, "rb") as f:
        database = pickle.load(f)
else:
    database = {}

# Init InsightFace
face_app = FaceAnalysis(name="buffalo_sc", providers=['CPUExecutionProvider'])
face_app.prepare(ctx_id=0)

# Camera
cap = cv2.VideoCapture(1)
if not cap.isOpened():
    print(" Could not access the camera.")
    sys.exit(1)

# Tkinter GUI
root = tk.Tk()
root.title("Face Registration")

# Center window
window_width, window_height = 550, 400
screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()
x_pos = (screen_width // 2) - (window_width // 2)
y_pos = (screen_height // 2) - (window_height // 2)
root.geometry(f"{window_width}x{window_height}+{x_pos}+{y_pos}")

# Canvas
canvas = tk.Canvas(root, width=window_width, height=window_height)
canvas.pack()
# Status label for visual feedback
status_label = tk.Label(root, text="", font=("Arial", 14), fg="red")
status_label.pack(pady=10)

# Bigger, oval-shaped bounding box
circle_bbox = (180, 60, 350, 280)
canvas.create_oval(*circle_bbox, outline="green", width=3)

def get_circle_masked_frame():
    ret, frame = cap.read()
    if not ret:
        return None
    frame = cv2.resize(frame, (window_width, window_height))
    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    # Create oval mask
    mask = np.zeros((window_height, window_width), dtype=np.uint8)
    cv2.ellipse(mask,
                ((circle_bbox[0]+circle_bbox[2])//2, (circle_bbox[1]+circle_bbox[3])//2),
                ((circle_bbox[2]-circle_bbox[0])//2, (circle_bbox[3]-circle_bbox[1])//2),
                0, 0, 360, 255, -1)

    masked = cv2.bitwise_and(rgb, rgb, mask=mask)
    return ImageTk.PhotoImage(image=Image.fromarray(masked))

def register_face(event=None):
    ret, frame = cap.read()
    if not ret:
        status_label.config(text=" Camera error.")
        return

    frame = cv2.resize(frame, (window_width, window_height))
    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    faces = face_app.get(rgb)
    if not faces:
        status_label.config(text=" No face detected. Try again.")
        return

    for face in faces:
        # Get face center
        x1, y1, x2, y2 = map(int, face.bbox)
        center_x = (x1 + x2) // 2
        center_y = (y1 + y2) // 2

        if (circle_bbox[0] < center_x < circle_bbox[2]) and (circle_bbox[1] < center_y < circle_bbox[3]):
            # Save the cropped oval image
            x0, y0, x1_, y1_ = circle_bbox
            face_crop = frame[y0:y1_, x0:x1_]
            cv2.imwrite(save_path, face_crop)

            # Save embedding from full frame
            embedding = face.embedding
            database[enrollment_no] = embedding
            with open(db_path, "wb") as f:
                pickle.dump(database, f)

            status_label.config(text="Face registered successfully!", fg="green")
            root.after(1500, root.destroy)  # Auto-close after 1.5s
            return

    status_label.config(text="️ Face not inside the oval. Adjust position.")



def update_frame():
    ret, frame = cap.read()
    if not ret:
        return

    frame = cv2.resize(frame, (window_width, window_height))
    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    # Make background white
    white_bg = np.full(rgb.shape, 255, dtype=np.uint8)

    # Create oval mask
    mask = np.zeros((window_height, window_width), dtype=np.uint8)
    center = ((circle_bbox[0] + circle_bbox[2]) // 2, (circle_bbox[1] + circle_bbox[3]) // 2)
    axes = ((circle_bbox[2] - circle_bbox[0]) // 2, (circle_bbox[3] - circle_bbox[1]) // 2)
    cv2.ellipse(mask, center, axes, 0, 0, 360, 255, -1)

    # Combine oval part from camera with white background
    combined = np.where(mask[:, :, None] == 255, rgb, white_bg)
    cv2.ellipse(combined, center, axes, 0, 0, 360, (0, 255, 0), 2)  #green border


    img = ImageTk.PhotoImage(image=Image.fromarray(combined))
    canvas.create_image(0, 0, anchor=tk.NW, image=img)
    canvas.img = img

    root.after(10, update_frame)


# Space key = register
root.bind("<space>", register_face)
print(" Place your face inside the oval and press SPACE to register.")
update_frame()
root.mainloop()

# Cleanup
cap.release()
cv2.destroyAllWindows()
