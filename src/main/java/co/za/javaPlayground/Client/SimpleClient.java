package co.za.javaPlayground.Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server. What would you like to do next?");
            String userInput;
            Thread readThread = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            readThread.start();

            while (true) {
                System.out.print("> ");
                userInput = scanner.nextLine();
                out.println(userInput);
                if (userInput.startsWith("quit")) {
                    break;
                }
            }

            socket.close(); // Ensure the socket is closed properly
            readThread.join(); // Wait for the read thread to finish

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
