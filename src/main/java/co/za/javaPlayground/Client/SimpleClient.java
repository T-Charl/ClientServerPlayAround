//package co.za.javaPlayground.Client;
//
//import java.io.*;
//import java.net.*;
//import java.util.Scanner;
//
//public class SimpleClient {
//    public static void main(String[] args) {
//        try (Socket socket = new Socket("localhost", 5000);
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             Scanner scanner = new Scanner(System.in)) {
//
//            System.out.println("Connected to the server. What would you like to do next?");
//            String userInput;
//            Thread readThread = new Thread(() -> {
//                try {
//                    String serverResponse;
//                    while ((serverResponse = in.readLine()) != null) {
//                        System.out.println(serverResponse);
//                    }
//                } catch (IOException e) {
//                    System.out.println("Disconnected from server.");
//                }
//            });
//            readThread.start();
//
//            while (true) {
//                System.out.print("> ");
//                userInput = scanner.nextLine();
//                out.println(userInput);
//                if (userInput.startsWith("quit")) {
//                    break;
//                }
//            }
//
//            socket.close(); // Ensure the socket is closed properly
//            readThread.join(); // Wait for the read thread to finish
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}

package co.za.javaPlayground.Client;

//client that reads ip and port addresses from server properties file

import Utilities.ClientConfig;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class SimpleClient {
    public static void main(String[] args) {
        String ipAddress = "127.0.0.1"; // Default to localhost
        int port = 1234; // Default port

        try {
            ClientConfig config = new ClientConfig("server.properties");
            ipAddress = config.getProperty("server.ip");
            port = Integer.parseInt(config.getProperty("server.port"));
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading config file, using defaults: " + e.getMessage());
        }

        try (
                Socket socket = new Socket(ipAddress, port);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to server. Type 'quit' to exit.");
            String userInput;

            Thread listenerThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Server: " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listenerThread.start();

            while (true) {
                System.out.print("Enter command: ");
                userInput = scanner.nextLine();

                out.println(userInput);
                out.flush();

                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
