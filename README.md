🎓 Virtual Classroom
A full-featured Java-based Virtual Classroom application built with Swing GUI, supporting Admin, Teacher, and Student roles. This platform integrates quizzes, lecture uploads, whiteboard collaboration, and real-time communication via networking and multithreading.
🚀 Features
- Multi-role Login System: Admin, Teacher, and Student authentication
- Quiz Module: Teachers can create and manage quizzes for students
- Lecture Uploads: Teachers can upload video lectures and content
- Whiteboard Feature: Shared whiteboard hosted on the server for visual teaching
- Networking & Multithreading: Real-time interactions using Java Sockets
- User Interface: Built using Java Swing for a desktop experience
🧑‍💻 Technologies Used
- Java (JDK 8+)
- Swing (GUI)
- MySQL (Database)
- NetBeans IDE
- Java Sockets & Threads
📂 Project Structure
Virtual_Classroom/
├── src/                      # Java source files
├── database/                 # SQL database schema
├── build.xml                 # Ant build file
├── manifest.mf               # Manifest file
└── README.md                 # Project documentation
🛠️ Getting Started
### Prerequisites
- JDK 8 or higher
- NetBeans IDE
- MySQL Server
🔄 Cloning and Running the Project
1. Clone the Repository:
   git clone https://github.com/apgit2610/Java-Project.git

2. Open in NetBeans:
   - Launch NetBeans.
   - Click on File > Open Project.
   - Navigate to and open the Java-Project folder.

3. Build and Run:
   - Right-click the project in the Projects pane.
   - Choose Build then Run.
🗄️ Database Setup
1. Create the Database:

CREATE DATABASE virtual_classroom;
USE virtual_classroom;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'teacher', 'student') NOT NULL
);

CREATE TABLE lectures (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL
);

CREATE TABLE videos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content LONGBLOB NOT NULL
);

INSERT INTO users (username, password, role)
VALUES ('admin', '1234', 'admin');


2. Update Connection Settings:
Modify the database connection code in your Java files to match your local MySQL credentials:
java
String url = "jdbc:mysql://localhost:3306/virtual_classroom";
String user = "your_mysql_username";
String password = "your_mysql_password";

📌 Notes
- Ensure your MySQL server is running before launching the application.
- For optimal performance, run the server component first before connecting clients.
- GUI may differ slightly based on your OS and screen resolution.
🤝 Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you'd like to change.
