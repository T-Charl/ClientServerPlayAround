package co.za.javaPlayground.Server;

import co.za.javaPlayground.Commands.Client.Command;
import co.za.javaPlayground.Commands.CommandHandler;
import Utilities.CommandParser;
import Utilities.ResponseHandler;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import java.util.List;


public class ClientCommandHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintStream out;
    private final World world;
    private final List<Robot> activeRobots;
    private final List<PrintStream> clients;

    public ClientCommandHandler(Socket socket, World world, List<Robot> activeRobots, List<PrintStream> clients) throws IOException {
        this.socket = socket;
        this.world = world;
        this.activeRobots = activeRobots;
        this.clients = clients;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintStream(socket.getOutputStream());
        clients.add(out);
    }

    @Override
    public void run() {
        try {
            String messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("Message from client: " + messageFromClient);
                try {
                    JsonObject request = CommandParser.parseCommand(messageFromClient);
//                    System.out.println("Parsed request: " + request); // Debug statement
//                    System.out.println("Message from client: " + messageFromClient);

//
                    String commandName = request.get("command").getAsString();
                    Command command = CommandHandler.getCommand(commandName, request);
                    JsonObject response = command.execute(request, world);
                    System.out.println("clientcomhand Tester!!Tester   " + request.toString());

                    out.println(response.toString());
                    if (response.has("message")) {
                        broadcast(response.get("message").getAsString());
                    }

                    if ("quit".equalsIgnoreCase(commandName)) {
                        String robotName = request.get("robot").getAsString();
                        world.removeRobot(robotName);
                        broadcast("Robot " + robotName + " has left the server.");
                        break;
                    }

                    if ("launch".equalsIgnoreCase(commandName)) {
                        String robotName = request.get("robot").getAsString();
                        broadcast("Robot " + robotName + " of type " + request.get("arguments").getAsJsonArray().get(0).getAsString() + " has joined the server.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println(ResponseHandler.createErrorResponse("Invalid command or arguments"));
                }
            }
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            clients.remove(out);
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Failed to close socket: " + e.getMessage());
            }
        }
    }

    private void broadcast(String message) {
        for (PrintStream client : clients) {
            client.println("Server: " + message);
        }
    }
}
