import java.io.*;
import java.net.*;

public class ClientSocketTest {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to server on localhost:5000...");
            Socket socket = new Socket("localhost", 5000);
            System.out.println("✅ Connected to server!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Salman"); // Simulate entering name

            // Read welcome message or chat history
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("❌ Could not connect to server:");
            e.printStackTrace();
        }
    }
}
