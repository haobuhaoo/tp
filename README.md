# ClassConnect

[![Java CI](https://github.com/AY2526S1-CS2103T-F12-2/tp/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/AY2526S1-CS2103T-F12-2/tp/actions/workflows/gradle.yml)

![Ui](docs/images/Ui.png)

## Overview
**ClassConnect** is a desktop app for **private tutors managing 20–40 students**.
It streamlines the tracking of student profiles — including **contacts, tuition timings, homework deadlines, exam dates, payments, and performance notes** — all in a simple **command-line interface (CLI)**.

Unlike tuition-center management systems, ClassConnect is built for **speed, simplicity, and solo use**. Tutors can manage lessons and admin tasks efficiently without bloated dashboards.

👉 To get started, check out the [User Guide](docs/UserGuide.md)
👉 For code/design details, see the [Developer Guide](docs/DeveloperGuide.md)

---

## Target User
- **Private tutors** with small to medium groups of students.
- Need to handle **student + parent contact details, lesson times, homework, payments, and progress notes**.  
- Prefer **fast CLI commands** over heavy GUIs.  

---

## Value Proposition
- **Unified system**: All student data and progress in one place.  
- **Speed-focused**: Lightweight commands optimized for tutor workflows.  
- **Error prevention**: Input validation for names, times, dates, and payments.  
- **Flexibility**: Archive/recover students, filter/search, and group by sessions.  

---

## Tech & Documentation
- **Written in Java, OOP-based**, adapted from [AB3](https://se-education.org/addressbook-level3).
- **Gradle-based project** with CI/CD via GitHub Actions.
- User Guide (UG) and Developer Guide (DG) in `/docs`.

---

## Credits
This project builds upon the [se-edu AddressBook Level 3](https://se-education.org/addressbook-level3) codebase created by the [SE-EDU initiative](https://se-education.org) and adapts it for the NUS CS2103T team project.
