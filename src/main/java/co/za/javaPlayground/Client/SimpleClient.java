package co.za.javaPlayground.Client;

import Utilities.CommandParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             PrintStream out = new PrintStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to server. Type 'quit' to exit.");

            while (true) {
                System.out.print("Enter command: ");
                String userInput = scanner.nextLine();
                JsonObject request;

                try {
//                    request = CommandParser.parseCommand(userInput);
                    out.println(userInput);
//                    out.flush();

                    String serverResponse = in.readLine();
                    if (serverResponse != null) {
                        JsonObject response = JsonParser.parseString(serverResponse).getAsJsonObject();
                        System.out.println(response);
//                        System.out.println("Server: " + response.get("message").getAsString());
                    }

                    if (userInput.startsWith("quit")) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


