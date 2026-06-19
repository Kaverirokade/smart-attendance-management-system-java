CREATE DATABASE student_db;

USE student_db;

CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    enrollment_no VARCHAR(20) NOT NULL UNIQUE,
    contact VARCHAR(15) NOT NULL,
    course VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    address TEXT NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    image_path VARCHAR(100),
    face_embedding JSON NOT NULL,
    created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS attendance (
    id VARCHAR(36) PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    timestamp DATETIME NOT NULL,
    similarity FLOAT,
    FOREIGN KEY (student_id) REFERENCES students(id)
);