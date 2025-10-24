import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 5000;
    private static Set<Socket> clients = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = in.readLine()) != null) {
                    for (Socket client : clients) {
                        if (!client.equals(socket)) {
                            new PrintWriter(client.getOutputStream(), true).println("Client: " + message);
                       }
                    }
                }
            } catch (IOException e) {
                clients.remove(socket);
            }
        }
    }
}
