package app;

import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ClientHandler extends Thread {
    ServerSocket server;
    BufferedReader inDalClient;
    OutputStream os;
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
        
        BufferedImage bImage = ImageIO.read(new File("/home/studente/Desktop/singh_tipsit/myapp/htdocs/immagine.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));


        String httpHeader;
        os = client.getOutputStream();
        
        
        request = inDalClient.readLine();
        
        System.out.println(request);
        
        resource = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><link rel=\"stylesheet\" href=\"style.css\"><title>Index</title></head><body><h1>Index</h1><p>Indice dei contenuti</p><ul><li>Link al file <a href=\"immagine.jpg\">immagine.jpg</a></li><li>Link al documento <a href=\"documento.pdf\">documento.pdf</a></li></ul><p>Immagine:</p><img src=\"immagine.jpg\" alt=\"natura\" /></body></html>";
        
        
        if (request.contains("GET / HTTP/1.1")) {
            httpHeader
                    = "HTTP/1.1 200 OK\n\r"
                    + "Content-Type: text/html\n\r"
                    + "Content-Lenght: " + resource.length()
                    + "\n\r"
                    + "\n\r"
                    + resource;

            os.write(httpHeader.getBytes(), 0, httpHeader.length());
            os.flush();
        }
        if(request.contains("style.css")){
            resource = "body {background-color: #fffdf0;color: #600;}";
            System.out.println("Test");
            httpHeader
                    = "HTTP/1.1 200 OK\n\r"
                    + "Content-Type: text/css\n\r"
                    + "Content-Lenght: " + resource.length()
                    + "\n\r"
                    + "\n\r"
                    + resource;

            os.write(httpHeader.getBytes(), 0, httpHeader.length());
            os.flush();
        }
        if(request.contains("immagine.jpg")){
            
            System.out.println("Test");
            httpHeader
                    = "HTTP/1.1 200 OK\n\r"
                    + "Content-Type: image/jpg;Base64\n\r"
                    + "Content-Lenght: " + data.length
                    + "\n\r"
                    + "\n\r"
                    + data;


            os.write(httpHeader.getBytes(), 0, httpHeader.length());
            os.flush();
        }
        
        client.close();

    }

}
