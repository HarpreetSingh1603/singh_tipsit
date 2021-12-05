package app;

import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

public class ClientHandler extends Thread {
    ServerSocket server;
    BufferedReader inDalClient;
    OutputStream os;
    String request;
    String resource;
    String badResource;
    File f;
    Path path;
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
        path = new File("/home/studente/Desktop/singh_tipsit/myapp/htdocs/immagine.jpg").toPath();
        
       /* String type = Files.probeContentType(path);
        f = new File("/home/studente/Desktop/singh_tipsit/myapp/htdocs/immagine.jpg");
        byte[] fileContent = FileUtils.readFileToByteArray(new File("/home/studente/Desktop/singh_tipsit/myapp/htdocs/immagine.jpg"));
        String encodestring;
        encodestring = Base64.getEncoder().encodeToString(fileContent);
        
        System.out.println(encodestring);*/
        byte[] data = Files.readAllBytes(path);
        
        
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
                    + "Content-Type: image/jpg\n\r"
                    + "Content-Lenght: " + path.toFile().length()
                    + "\n\r"
                    + "\n\r";
            
            byte[] stringa = httpHeader.getBytes();
            byte[] bytes = new byte[data.length + stringa.length];
            System.arraycopy(stringa, 0, bytes, 0, stringa.length);
            System.arraycopy(data, 0, bytes, stringa.length, data.length);

            
            os.write(bytes);
            os.flush();
        }
        
        client.close();

    }

}
