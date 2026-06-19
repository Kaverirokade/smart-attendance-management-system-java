@echo off
echo Running Attendance Script using global Python...

:: Set working directory (optional but recommended)
cd /d "C:\Users\Manu Kumar\Desktop\SmartAttendance\FaceAttendanceManagmentSystem\src\PythonServer\PythonClient"

:: Use global Python to run the script
python attendance.py

:: Print exit status
echo Exit Code: %ERRORLEVEL%

pause

