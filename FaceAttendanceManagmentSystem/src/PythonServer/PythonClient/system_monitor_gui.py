import psutil
import GPUtil
import tkinter as tk
from tkinter import ttk, Menu, Frame, Label
import platform
from datetime import datetime

running = False
monitor_window = None
config = {
    'bg_color': '#0a0e14',
    'fg_color': '#c5c9c5',
    'accent_color': '#5ccfe6',
    'font': ('Segoe UI', 10),
    'interval': 1000,
    'position': (0, 0),
    'show_ram': True,
    'show_gpu_mem': True
}

old_recv = psutil.net_io_counters().bytes_recv
old_sent = psutil.net_io_counters().bytes_sent

def format_speed(bps):
    kb = bps / 1024
    mb = kb / 1024
    return f"{mb:.2f} MB/s" if mb >= 1 else f"{kb:.2f} KB/s"

def get_cpu_freq():
    try:
        freq = psutil.cpu_freq()
        return f"{freq.current/1000:.2f}/{freq.max/1000:.2f}GHz"
    except:
        return "N/A"

def get_gpu_info():
    try:
        gpus = GPUtil.getGPUs()
        if gpus:
            gpu = gpus[0]
            return {
                'model': gpu.name,
                'load': gpu.load * 100,
                'mem_used': gpu.memoryUsed,
                'mem_total': gpu.memoryTotal,
                'mem_percent': gpu.memoryUtil * 100
            }
    except:
        pass
    return {'model': "NVIDIA GeForce", 'load': 0, 'mem_used': 0, 
            'mem_total': 1, 'mem_percent': 0}

def update_stats():
    global old_recv, old_sent
    if running:
        # Network stats
        new_recv = psutil.net_io_counters().bytes_recv
        new_sent = psutil.net_io_counters().bytes_sent
        down = new_recv - old_recv
        up = new_sent - old_sent
        old_recv, old_sent = new_recv, new_sent
        
        # CPU stats
        cpu_percent = psutil.cpu_percent(interval=0.3)
        cpu_freq = get_cpu_freq()
        
        # RAM stats
        ram = psutil.virtual_memory()
        ram_percent = ram.percent
        ram_used = ram.used // (1024 ** 3)
        ram_total = ram.total // (1024 ** 3)
        
        # GPU stats
        gpu = get_gpu_info()
        
        # Update UI
        net_label.config(text=f"⬇ {format_speed(down)}  ⬆ {format_speed(up)}")
        cpu_label.config(text=f"CPU\n{cpu_freq}\n{cpu_percent:.1f}%")
        gpu_label.config(text=f"GPU\n{gpu['load']:.1f}%")
        vram_label.config(text=f"VRAM\n{gpu['mem_percent']:.1f}%")
        
        # Update progress bars
        cpu_bar['value'] = cpu_percent
        gpu_bar['value'] = gpu['load']
        vram_bar['value'] = gpu['mem_percent']
        
        # Update time
        time_label.config(text=datetime.now().strftime("%H:%M:%S"))

    if running:
        monitor_window.after(config['interval'], update_stats)

def exit_all():
    global running
    running = False
    if monitor_window:
        monitor_window.destroy()
    control_panel.destroy()

def create_monitor_window():
    global monitor_window, net_label, cpu_label, gpu_label, vram_label
    global cpu_bar, gpu_bar, vram_bar, time_label

    screen_width = control_panel.winfo_screenwidth()
    bar_width = 500
    x_pos = screen_width - bar_width - 20 if config['position'] == (0, 0) else config['position'][0]
    y_pos = 50 if config['position'] == (0, 0) else config['position'][1]

    monitor_window = tk.Toplevel()
    monitor_window.title("System Monitor")
    monitor_window.geometry(f"{bar_width}x180+{x_pos}+{y_pos}")
    monitor_window.overrideredirect(True)
    monitor_window.attributes("-topmost", True)
    monitor_window.configure(bg=config['bg_color'])
    
    # Header with model info
    gpu_info = get_gpu_info()
    header_frame = Frame(monitor_window, bg="#161b22", height=30)
    header_frame.pack(fill="x", padx=1, pady=(1, 0))
    model_label = Label(header_frame, text=f"{gpu_info['model']}", 
                      fg=config['accent_color'], bg="#161b22", font=("Segoe UI", 9))
    model_label.pack(side="left", padx=10)
    
    # Time display
    time_label = Label(header_frame, text="00:00:00", 
                     fg=config['fg_color'], bg="#161b22", font=("Segoe UI", 9))
    time_label.pack(side="right", padx=10)
    
    # Main content frame
    content_frame = Frame(monitor_window, bg=config['bg_color'])
    content_frame.pack(fill="both", expand=True, padx=10, pady=10)
    
    # Network stats
    net_frame = Frame(content_frame, bg=config['bg_color'])
    net_frame.grid(row=0, column=0, columnspan=4, sticky="ew", pady=(0, 10))
    net_label = Label(net_frame, text="⬇ 0.00 KB/s  ⬆ 0.00 KB/s", 
                     fg=config['accent_color'], bg=config['bg_color'], font=("Segoe UI", 10, "bold"))
    net_label.pack()
    
    # CPU section
    cpu_frame = Frame(content_frame, bg=config['bg_color'])
    cpu_frame.grid(row=1, column=0, padx=10, sticky="w")
    Label(cpu_frame, text="CPU", fg=config['fg_color'], bg=config['bg_color'], 
          font=("Segoe UI", 9)).pack(anchor="w")
    cpu_label = Label(cpu_frame, text="0.00/0.00GHz\n0%", 
                    fg=config['accent_color'], bg=config['bg_color'], font=("Segoe UI", 9))
    cpu_label.pack(anchor="w")
    cpu_bar = ttk.Progressbar(cpu_frame, orient="horizontal", length=100, mode="determinate")
    cpu_bar.pack(fill="x", pady=(5, 0))
    
    # GPU section
    gpu_frame = Frame(content_frame, bg=config['bg_color'])
    gpu_frame.grid(row=1, column=1, padx=10, sticky="w")
    Label(gpu_frame, text="GPU", fg=config['fg_color'], bg=config['bg_color'], 
          font=("Segoe UI", 9)).pack(anchor="w")
    gpu_label = Label(gpu_frame, text="0%", 
                    fg=config['accent_color'], bg=config['bg_color'], font=("Segoe UI", 9))
    gpu_label.pack(anchor="w")
    gpu_bar = ttk.Progressbar(gpu_frame, orient="horizontal", length=100, mode="determinate")
    gpu_bar.pack(fill="x", pady=(5, 0))
    
    # VRAM section
    vram_frame = Frame(content_frame, bg=config['bg_color'])
    vram_frame.grid(row=1, column=2, padx=10, sticky="w")
    Label(vram_frame, text="VRAM", fg=config['fg_color'], bg=config['bg_color'], 
          font=("Segoe UI", 9)).pack(anchor="w")
    vram_label = Label(vram_frame, text="0%", 
                     fg=config['accent_color'], bg=config['bg_color'], font=("Segoe UI", 9))
    vram_label.pack(anchor="w")
    vram_bar = ttk.Progressbar(vram_frame, orient="horizontal", length=100, mode="determinate")
    vram_bar.pack(fill="x", pady=(5, 0))
    
    # RAM section
    ram_frame = Frame(content_frame, bg=config['bg_color'])
    ram_frame.grid(row=1, column=3, padx=10, sticky="w")
    Label(ram_frame, text="RAM", fg=config['fg_color'], bg=config['bg_color'], 
          font=("Segoe UI", 9)).pack(anchor="w")
    ram = psutil.virtual_memory()
    ram_used = ram.used // (1024 ** 3)
    ram_total = ram.total // (1024 ** 3)
    ram_label = Label(ram_frame, text=f"{ram_used}GB/{ram_total}GB\n{ram.percent:.1f}%", 
                    fg=config['accent_color'], bg=config['bg_color'], font=("Segoe UI", 9))
    ram_label.pack(anchor="w")
    ram_bar = ttk.Progressbar(ram_frame, orient="horizontal", length=100, mode="determinate")
    ram_bar.pack(fill="x", pady=(5, 0))
    ram_bar['value'] = ram.percent
    
    # Style progress bars
    style = ttk.Style()
    style.theme_use('clam')
    style.configure("TProgressbar", thickness=5, troughcolor='#1a1f29', 
                   background=config['accent_color'])
    
    # Drag functionality
    def start_drag(e): 
        monitor_window.x = e.x
        monitor_window.y = e.y
        
    def drag(e):
        x = monitor_window.winfo_pointerx() - monitor_window.x
        y = monitor_window.winfo_pointery() - monitor_window.y
        monitor_window.geometry(f"+{x}+{y}")
        config['position'] = (x, y)

    header_frame.bind("<Button-1>", start_drag)
    header_frame.bind("<B1-Motion>", drag)

    def show_menu(e):
        menu = Menu(monitor_window, tearoff=0, bg="#1a1f29", fg=config['fg_color'],
                    activebackground="#252d3a", activeforeground="#ffffff")
        menu.add_command(label="Settings", command=open_settings)
        menu.add_command(label="Exit", command=exit_all)
        menu.tk_popup(e.x_root, e.y_root)

    header_frame.bind("<Button-3>", show_menu)

    update_stats()

def open_settings():
    settings = tk.Toplevel(control_panel)
    settings.title("Monitor Settings")
    settings.geometry("300x200")
    settings.resizable(False, False)
    settings.configure(bg="#1a1f29")
    settings.attributes("-topmost", True)
    settings.grab_set()
    
    style = ttk.Style()
    style.configure("TFrame", background="#1a1f29")
    style.configure("TLabel", background="#1a1f29", foreground=config['fg_color'])
    style.configure("TCheckbutton", background="#1a1f29", foreground=config['fg_color'])
    style.configure("TButton", background="#252d3a", foreground=config['fg_color'])
    
    frame = ttk.Frame(settings, padding=20)
    frame.pack(fill="both", expand=True)
    
    # Toggles
    ram_var = tk.BooleanVar(value=config['show_ram'])
    gpu_mem_var = tk.BooleanVar(value=config['show_gpu_mem'])
    
    def save_settings():
        config['show_ram'] = ram_var.get()
        config['show_gpu_mem'] = gpu_mem_var.get()
        settings.destroy()
    
    ttk.Checkbutton(frame, text="Show RAM Usage", variable=ram_var).pack(anchor="w", pady=5)
    ttk.Checkbutton(frame, text="Show GPU Memory", variable=gpu_mem_var).pack(anchor="w", pady=5)
    
    # Save button
    btn_frame = ttk.Frame(frame)
    btn_frame.pack(fill="x", pady=(20, 0))
    ttk.Button(btn_frame, text="Save", command=save_settings).pack(side="right", padx=5)
    ttk.Button(btn_frame, text="Cancel", command=settings.destroy).pack(side="right")

def start_monitor():
    global running
    if not running:
        running = True
        create_monitor_window()
        control_panel.withdraw()

# Create control panel
control_panel = tk.Tk()
control_panel.title("System Monitor Control")
control_panel.geometry("300x120")
control_panel.resizable(False, False)
control_panel.configure(bg="#1a1f29")

# Styling
style = ttk.Style()
style.configure("TFrame", background="#1a1f29")
style.configure("TButton", background="#252d3a", foreground=config['fg_color'], 
                font=("Segoe UI", 9), borderwidth=0)
style.map("TButton", background=[("active", "#2d3848")])

# Header
header = ttk.Frame(control_panel)
header.pack(fill="x", padx=20, pady=(15, 10))
ttk.Label(header, text="SYSTEM MONITOR", font=("Segoe UI", 12, "bold"), 
          foreground=config['accent_color'], background="#1a1f29").pack()

# Buttons
btn_frame = ttk.Frame(control_panel)
btn_frame.pack(pady=10)

start_btn = ttk.Button(btn_frame, text="Start Monitor", command=start_monitor, width=15)
start_btn.pack(side="left", padx=5)

settings_btn = ttk.Button(btn_frame, text="Settings", command=open_settings, width=10)
settings_btn.pack(side="left", padx=5)

exit_btn = ttk.Button(control_panel, text="Exit", command=exit_all, width=10)
exit_btn.pack(pady=(10, 15))

control_panel.mainloop()
