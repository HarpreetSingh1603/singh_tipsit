package app;

import java.net.*;
import java.io.*;
import java.lang.*;


public class HttpServer {
    
    public void start() {
        
        try {
            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("Server partito");
            for (;;) {
                System.out.println("Server in attesa");
                Socket client = serverSocket.accept();
                
                ClientHandler clientHandler = new ClientHandler(client);
                
                clientHandler.start();
            }
            
            
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Errore instanza server");
        }
        
    }
    
    public static void main(String[] args){
        HttpServer server = new HttpServer();
        
        server.start();
    }
}
