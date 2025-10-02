# ClassConnect  

[![Java CI](https://github.com/AY2526S1-CS2103T-F12-2/tp/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/AY2526S1-CS2103T-F12-2/tp/actions/workflows/gradle.yml)

![Ui](docs/images/Ui.png)

![Ui](docs/images/Ui.png)

## 📌 Overview  
**ClassConnect** is a desktop app for **private tutors managing 20–40 students**.  
It streamlines the tracking of student profiles — including **contacts, tuition timings, homework deadlines, exam dates, payments, and performance notes** — all in a simple **command-line interface (CLI)**.

Unlike tuition-center management systems, ClassConnect is built for **speed, simplicity, and solo use**. Tutors can manage lessons and admin tasks efficiently without bloated dashboards.

---

## 🎯 Target User  
- **Private tutors** with small to medium groups of students.  
- Need to handle **student + parent contact details, lesson times, homework, payments, and progress notes**.  
- Prefer **fast CLI commands** over heavy GUIs.  

---

## 💡 Value Proposition  
- **Unified system**: All student data and progress in one place.  
- **Speed-focused**: Lightweight commands optimized for tutor workflows.  
- **Error prevention**: Input validation for names, times, dates, and payments.  
- **Flexibility**: Archive/recover students, filter/search, and group by sessions.  

---

## ✅ User Stories  
- As a tutor, I can **add students** so I can start tracking them.  
- As a tutor, I can **delete students** so I can remove those who left.  
- As a tutor, I can **record homework with deadlines** so I can remind and follow up.  
- As a tutor, I can **track payments** so I know which students have overdue fees.  
- As a tutor, I can **archive old students** so my current list stays uncluttered.  
- As a tutor, I can **store parent contacts** so I can reach guardians easily.  

---

## 📘 Use Cases  

### Use Case 1: Add Student  
**Actor:** Tutor  
**Goal:** Add a new student with contact info and lesson time.  
**Scenario:**  
1. Tutor enters `add-student n/Marcus p/98765432 t/Mon 1900 lvl/Sec3 sub/Math`.  
2. System validates input and stores the student record.  
3. System confirms:  
   *“Student added: Marcus, 98765432, Mon 19:00, Sec3, Math”*  

---

### Use Case 2: Record Homework  
**Actor:** Tutor  
**Goal:** Track homework with deadlines.  
**Scenario:**  
1. Tutor enters `add-homework sid/1 d/Finish Ch.3 problems due/2025-10-05`.  
2. System validates and links it to the student.  
3. Tutor later runs `list-homework filter/overdue` to see overdue tasks.  

---

### Use Case 3: Track Payments  
**Actor:** Tutor  
**Goal:** Record tuition fees and check arrears.  
**Scenario:**  
1. Tutor enters `record-payment sid/1 amt/240 notes/Sep tuition`.  
2. Payment is stored as **UNPAID**.  
3. Tutor marks it as paid later with `pay 3`.  
4. Running `list-arrears` shows unpaid students.  

---

## 🔧 Tech & Documentation  
- **Written in Java, OOP-based**, adapted from [AB3](https://se-education.org/addressbook-level3).  
- **Gradle-based project** with CI/CD via GitHub Actions.  
- User Guide (UG) and Developer Guide (DG) in `/docs`.  

---

## 🙌 Credits  
This project builds upon the [se-edu AddressBook Level 3](https://se-education.org/addressbook-level3) codebase and adapts it for the NUS CS2103T team project.  