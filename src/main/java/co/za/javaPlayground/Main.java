//package co.za.javaPlayground;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Main {
//    public static void main(String[] args) {
//        try (ServerSocket serverSocket = new ServerSocket(SimpleServer.PORT)) {
//            System.out.println("Server started on port " + SimpleServer.PORT);
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("Server in while loop");
//                SimpleServer server = new SimpleServer(clientSocket);
//                new Thread(server).start();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//}
