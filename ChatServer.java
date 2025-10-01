// filename: ChatServer.java
// Compile: javac ChatServer.java
// Run: java ChatServer

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server started. Waiting for client...");
        Socket socket = server.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Welcome to the server!");

        String msg;
        while ((msg = in.readLine()) != null) {
            System.out.println("Client: " + msg);
            out.println("Echo: " + msg);
        }
        socket.close();
        server.close();
    }
}
