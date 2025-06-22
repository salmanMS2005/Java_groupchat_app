import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        try {
            DatabaseManager.connect();
            System.out.println("Connected to MySQL Database.");
        } catch (Exception e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return;
        }

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server running on port 5000...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {

                    client.sendMessage(message);

            }
        }
    }

    static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //out.println("Enter your name: ");
            name = in.readLine();

            // Send chat history
            String history = DatabaseManager.getMessageHistory();
            if (!history.isEmpty()) {
                out.println("--- Chat History ---");
                out.println(history);
                out.println("--- End of History ---");
            }

            ChatServer.broadcast(name + " joined the chat!", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                String fullMessage = name + ": " + message;
                System.out.println(fullMessage);

                DatabaseManager.saveMessage(name, message);
                ChatServer.broadcast(fullMessage, this);
            }
        } catch (IOException e) {
            System.out.println(name + " disconnected.");
        } finally {
            try {
                socket.close();
                ChatServer.removeClient(this);
                ChatServer.broadcast(name + " left the chat.", this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }
}
