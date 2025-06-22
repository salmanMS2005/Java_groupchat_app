# 💬 Java Group Chat Application (Swing + MySQL)

A real-time group chat application built using **Java**, **Swing GUI**, **Socket Programming**, and **MySQL** database. Designed to simulate a WhatsApp-style group chat, this project allows multiple users to join, view chat history, and exchange messages in real time.

## 🚀 Features

- 🧑‍💻 GUI-based client using Java Swing
- 🔌 Server-client communication over TCP sockets
- 🧠 Stores and retrieves messages using MySQL
- 📜 Chat history is loaded for new users on join
- 🔁 Multiple users can chat simultaneously (real-time)
- ✅ Clean MVC structure with database separation

## 📁 Project Structure

chat_app/

├── ChatServer.java # Main server to accept and handle clients

├── ChatClientGUI.java # GUI client for sending/receiving messages

├── DatabaseManager.java # Handles MySQL connection and queries

├── .gitignore # Clean repo from junk files

└── README.md # You're reading it 😄



## 🛠️ Technologies Used

- Java (JDK 17+ or JDK 21+)
- Swing (GUI)
- MySQL 8.x
- Socket Programming
- IntelliJ IDEA

## ⚙️ How to Run

### ✅ 1. Setup MySQL Database

```sql
CREATE DATABASE chat_app;
USE chat_app;

CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    message TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
💡 Make sure MySQL is running and update credentials in DatabaseManager.java.


✅ 2. Run the Server


Run ChatServer.java


This will start the socket server on port 5000.

✅ 3. Run Multiple Clients


You can run ChatClientGUI.java multiple times (or in IntelliJ, enable Parallel Run).

Each client will:

Ask for your name

Then,Shows chat history (not if its first time)

Let you send & receive messages in real time

🐞 Troubleshooting

❌ Access denied for MySQL?

Check your MySQL username/password in DatabaseManager.java

❌ Can't connect to server?

Make sure ChatServer.java is running before the client

🧱 Client UI not responding?

Make sure you're not blocking the Swing UI thread (already handled in this code)

🧹 TODO / Improvements

🔒 User authentication

💬 Private messaging (1-on-1)

🌐 Host server on cloud (AWS / Heroku)

🖼️ File sharing or media preview

🧑‍💻 Author:

Salman M S

B.Tech - Artificial Intelligence & Data Science

St. Joseph’s College of Engineering

GitHub: @salmanms

