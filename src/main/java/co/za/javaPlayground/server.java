package co.za.javaPlayground;

import java.io.*;
import java.net.*;

public class server {
//
//    public static final int PORT = 12345;
//    private final BufferedReader in;
//    private final PrintStream out;
//    private final String clientMachine;
//
//    public server(Socket socket) throws IOException {
//        clientMachine = socket.getInetAddress().getHostName();
//        System.out.println("Connection from " + clientMachine);
//
//        out = new PrintStream(socket.getOutputStream());
//        in = new BufferedReader(new InputStreamReader(
//                socket.getInputStream()));
//        System.out.println("Waiting for client...");
//    }
//
//    public server(BufferedReader in, PrintStream out, String clientMachine) {
//        this.in = in;
//        this.out = out;
//        this.clientMachine = clientMachine;
//    }
//
//    public void run() {
//        try {
//            String messageFromClient;
//            while((messageFromClient = in.readLine()) != null) {
//                System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
//                out.println("Thanks for this message: "+messageFromClient);
//            }
//        } catch(IOException ex) {
//            System.out.println("Shutting down single client server");
//        } finally {
//            closeQuietly();
//        }
//    }
//
//    private void closeQuietly() {
//        try { in.close(); out.close();
//        } catch(IOException ex) {}
//    }

    public static void main(String args[]) throws IOException {
        ServerSocket socket   = new ServerSocket( 1234);
        Socket socket1 = socket.accept(); // Allows server to listen for and accept new threads
        System.out.println("Connected to server ");

    }
}
