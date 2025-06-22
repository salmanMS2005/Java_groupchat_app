import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI {

    private String userName;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private JFrame frame;
    private JTextArea messageArea;
    private JTextField inputField;

    public ChatClientGUI(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            userName = JOptionPane.showInputDialog("Enter your name:");
            writer.println(userName);  // Send name to server

            buildGUI();

            // Thread to receive messages
            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        messageArea.append(line + "\n");
                    }
                } catch (IOException e) {
                    showError("Disconnected from server.");
                }
            }).start();

        } catch (IOException e) {
            showError("Unable to connect to server.");
        }
    }

    private void buildGUI() {
        frame = new JFrame("Group Chat - " + userName);
        messageArea = new JTextArea(20, 50);
        inputField = new JTextField(40);

        messageArea.setEditable(false);
        inputField.setEnabled(true);
        inputField.setEditable(true);
        inputField.requestFocusInWindow();

        inputField.addActionListener(e -> {
            String msg = inputField.getText();
            if (!msg.trim().isEmpty()) {
                writer.println(msg);
                inputField.setText("");
            }
        });

        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.getContentPane().add(inputField, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClientGUI("localhost", 5000));
    }
}
