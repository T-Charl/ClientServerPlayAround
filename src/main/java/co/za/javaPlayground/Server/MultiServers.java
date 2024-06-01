////package co.za.javaPlayground.Server;
////
////import java.net.*;
////import java.io.*;
////
////public class MultiServers {
////    public static void main(String[] args) throws IOException {
////
////        ServerSocket s = new ServerSocket(SimpleServer.PORT);
////        System.out.println("Server running & waiting for client connections.");
////        while (true) {
////            try {
////                Socket socket = s.accept();
////                System.out.println("Connection: " + socket);
////
////                Runnable r = new SimpleServer(socket);
////                Thread task = new Thread(r);
////                task.start();
////            } catch (IOException ex) {
////                ex.printStackTrace();
////            }
////        }
////    }
////}
//
//
//
////package co.za.javaPlayground.Server;
//
////// codebase that includes logger for all server interactions
//
////import Utilities.ServerConfig;
//////import co.za.javaPlayground.Util.ConfigReader;
////
////import java.net.*;
////import java.io.*;
////import java.util.concurrent.ExecutorService;
////import java.util.concurrent.Executors;
////import java.util.logging.Logger;
////
////public class MultiServers {
////    private static final int THREAD_POOL_SIZE = 10;
////    private static final Logger logger = Logger.getLogger(MultiServers.class.getName());
////
////    public static void main(String[] args) {
////        try {
////            ServerConfig configReader = new ServerConfig("server.properties");
////            String ipAddress = configReader.getProperty("server.ip");
////            int port = Integer.parseInt(configReader.getProperty("server.port"));
////
////            InetAddress bindAddress = InetAddress.getByName(ipAddress);
////            ServerSocket serverSocket = new ServerSocket(port, 50, bindAddress);
////            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
////
////            logger.info("Server running & waiting for client connections on " + ipAddress + ":" + port);
////            while (true) {
////                try {
////                    Socket socket = serverSocket.accept();
////                    logger.info("Connection: " + socket);
////                    Runnable serverTask = new SimpleServer(socket);
////                    executorService.submit(serverTask);
////                } catch (IOException ex) {
////                    logger.severe("Connection error: " + ex.getMessage());
////                }
////            }
////        } catch (IOException e) {
////            logger.severe("Could not bind to address: " + e.getMessage());
////        }
////    }
////}
//
//
//
//
//package co.za.javaPlayground.Server;
//
//
//import java.net.*;
//import java.io.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//
//import Utilities.ServerConfig;
//
//
//public class MultiServers {
//    private static final int THREAD_POOL_SIZE = 10;
//
//    public static void main(String[] args) {
//        InetAddress bindAddress = null;
//        int port = 0;
//
//        try {
//            ServerConfig config = new ServerConfig("server.properties");
//            String ipAddress = config.getProperty("server.ip");
//            port = Integer.parseInt(config.getProperty("server.port"));
//
//            try {
//                bindAddress = InetAddress.getByName(ipAddress);
//            } catch (UnknownHostException e) {
//                System.err.println("Invalid IP address in config file: " + e.getMessage());
//                System.err.println("Server will not start. Please check the IP address in the config file.");
//                System.exit(1);
//            }
//        } catch (IOException | NumberFormatException e) {
//            System.err.println("Error reading config file: " + e.getMessage());
//            System.exit(1);
//        }
//
//        if (bindAddress == null) {
//            System.err.println("Binding address is null. Exiting.");
//            System.exit(1);
//        }
//
//        try (ServerSocket serverSocket = new ServerSocket(port, 50, bindAddress)) {
//            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//
//            System.out.println("Server running & waiting for client connections on " + bindAddress.getHostAddress() + ":" + port);
//            while (true) {
//                try {
//                    Socket socket = serverSocket.accept();
//                    System.out.println("Connection: " + socket);
//                    Runnable serverTask = new SimpleServer(socket);
//                    executorService.submit(serverTask);
//                } catch (IOException ex) {
//                    System.err.println("Connection error: " + ex.getMessage());
//                }
//            }
//        } catch (BindException e) {
//            System.err.println("Could not bind to address: " + e.getMessage());
//            System.err.println("Attempting to bind to localhost instead.");
//            try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("127.0.0.1"))) {
//                ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//
//                System.out.println("Server running & waiting for client connections on localhost:" + port);
//                while (true) {
//                    try {
//                        Socket socket = serverSocket.accept();
//                        System.out.println("Connection: " + socket);
//                        Runnable serverTask = new SimpleServer(socket);
//                        executorService.submit(serverTask);
//                    } catch (IOException ex) {
//                        System.err.println("Connection error: " + ex.getMessage());
//                    }
//                }
//            } catch (IOException ex) {
//                System.err.println("Failed to bind to localhost: " + ex.getMessage());
//            }
//        } catch (IOException e) {
//            System.err.println("Could not bind to address: " + e.getMessage());
//        }
//    }
//}
