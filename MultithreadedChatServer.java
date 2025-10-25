import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatServer {
    private static final int PORT = 5000;
    private static final Set<Socket> clients = Collections.synchronizedSet(new HashSet<>());
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        System.out.println(CYAN + "✨=============================================✨" + RESET);
        System.out.println(PURPLE + "💬  Aesthetic Multi-Client Chat Server  ☕" + RESET);
        System.out.println(GREEN + "🚀 Server starting on port: " + PORT + RESET);
        System.out.println(CYAN + "✨=============================================✨" + RESET);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                String clientInfo = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();

                log(GREEN, "🟢 " + clientInfo + " joined the chat!");
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            log(RED, "❌ Server error: " + e.getMessage());
        }
    }

    static void broadcast(String message, Socket sender) {
        synchronized (clients) {
            for (Socket client : clients) {
                if (!client.equals(sender)) {
                    try {
                        new PrintWriter(client.getOutputStream(), true).println(message);
                    } catch (IOException e) {
                        clients.remove(client);
                    }
                }
            }
        }
    }

    static void log(String color, String msg) {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println(color + "[" + time + "] " + msg + RESET);
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            String clientInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = in.readLine()) != null) {
                    String formattedMsg = YELLOW + "[" + clientInfo + "] " + RESET + message;
                    log(YELLOW, "💬 Message from " + clientInfo + ": " + message);
                    broadcast(formattedMsg, socket);
                }
            } catch (IOException e) {
                log(RED, "🔴 " + clientInfo + " disconnected.");
            } finally {
                clients.remove(socket);
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
