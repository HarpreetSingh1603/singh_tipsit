package app;

import java.net.*;
import java.io.*;

public class ClientHandler extends Thread {
    PrintWriter outVersoClient;
    ServerSocket server;
    BufferedReader inDalClient;
    String request;
    String resource;
    String badResource;
    
    Socket client;

    HttpServer utenti = new HttpServer();

    public ClientHandler(Socket client) {
        System.setProperty("line.separator", "\n\r");
        this.client = client;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void comunica() throws Exception {
        
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new PrintWriter(client.getOutputStream(), true);
        
        
        
        request = inDalClient.readLine();
        
        resource = "<!DOCTYPE html>\n<html><body><h1>Risorsa</h1></body></html>";
        badResource = "<!DOCTYPE html>\n<html><body><h1>errore</h1></body></html>";
        
        if(request.contains("GET")){
            outVersoClient.println("HTTP/1.1 200 OK");
            outVersoClient.println("Content-Type: text/html");
            outVersoClient.println("Content-Lenght: " + resource.length());
            outVersoClient.println("");
            outVersoClient.println(resource);            
        } else {
            outVersoClient.println("HTTP/1.1 400 Bad Request");
            outVersoClient.println("Content-Type: text/html");
            outVersoClient.println("Content-Lenght: " + badResource.length());
            outVersoClient.println("");
            outVersoClient.println(badResource);
        }
        
        //outVersoClient.println("Funziona!");
        
        
        client.close();

    }

}
