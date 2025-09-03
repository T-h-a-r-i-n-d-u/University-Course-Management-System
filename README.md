# 🎓 UCMS – University Course Management System

UCMS is a **full-stack course management platform** built to streamline teaching and learning in universities.  
It enables **Administrators, Lecturers, and Students** to collaborate on course creation, enrollment, assignments, lecture notes, and results.

---

## ✨ Features

### 👩‍🎓 Students
- Request enrollment into courses
- Access lecture notes
- Submit assignment answers
- View results and feedback

### 👨‍🏫 Lecturers
- Manage assigned courses
- Upload lecture notes
- Create and manage assignments
- Grade submissions and publish results

### 🛠️ Administrators
- Approve/reject user registrations
- Approve/reject course enrollments
- Assign lecturers to courses
- Generate reports on users, courses, and results

---

## 🖼️ System Overview

### Database Design
![Database Diagram](./db.png)

Entity-relationships cover **users, courses, enrollments, assignments, submissions, lecture notes, and results**, with clear links for academic workflows.

---

## ⚙️ Tech Stack

- **Frontend:** Angular 16 + Angular Material, served with **Nginx**
- **Backend:** Spring Boot 3 (Java 17), Spring Security (JWT), Spring Data JPA
- **Database:** PostgreSQL 16
- **Build & Deploy:** Docker, Docker Compose

---

## 📂 Project Structure

