import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket erverSocket = new ServerSocket(5000);
        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String message = in.readLine();
        out.println("Echo: " + message);
        socket.close();
        serverSocket.close();
    }
}
