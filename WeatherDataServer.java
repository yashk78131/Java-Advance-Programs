// filename: WeatherDataServer.java
// Compile: javac WeatherDataServer.java
// Run: java WeatherDataServer

import java.io.*;
import java.net.*;
import java.util.*;

public class WeatherDataServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5050);
        System.out.println("🌦 Weather Server started on port 5050");
        System.out.println("Waiting for client connection...");

        Socket socket = server.accept();
        System.out.println("Client connected successfully!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Welcome to the Weather Data Server!");
        out.println("Type a city name to get current temperature (dummy data). Type 'exit' to quit.");

        Map<String, Integer> weatherData = new HashMap<>();
        weatherData.put("Delhi", 31);
        weatherData.put("Mumbai", 29);
        weatherData.put("Bangalore", 27);
        weatherData.put("Chennai", 33);
        weatherData.put("Kolkata", 30);

        String city;
        while ((city = in.readLine()) != null) {
            if (city.equalsIgnoreCase("exit")) {
                out.println("Goodbye!");
                break;
            }

            if (weatherData.containsKey(city)) {
                out.println("Temperature in " + city + ": " + weatherData.get(city) + "°C");
            } else {
                out.println("City not found in database.");
            }
        }

        socket.close();
        server.close();
        System.out.println("Server closed.");
    }
}
