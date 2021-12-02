package app;

import java.net.*;
import java.io.*;

public class ClientHandler extends Thread {
    PrintWriter outVersoClient;
    ServerSocket server;

    Socket client;

    HttpServer utenti = new HttpServer();

    public ClientHandler(Socket client) {
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
        //Thread.sleep(5000);
        
        outVersoClient = new PrintWriter(client.getOutputStream());
        
        outVersoClient.write("Funziona!");
        outVersoClient.flush();
        
        client.close();

    }

}
