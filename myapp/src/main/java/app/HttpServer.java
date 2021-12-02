package app;

import java.net.*;
import java.io.*;
import java.lang.*;


public class HttpServer {
    
    public void start() {
        
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            
            Socket client = serverSocket.accept();
            
            
            PrintWriter outVersoClient;
            outVersoClient = new PrintWriter(client.getOutputStream());
            
            outVersoClient.write("Funziona!");
            outVersoClient.flush();
            
            
            
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
