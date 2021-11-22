import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(6789);
            for(;;){
                System.out.println("1 Server in attesa ...");
                Socket socket = serverSocket.accept();
                System.out.println("3 Server socket " + socket);
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
        } catch (Exception e){
            System.out.println("Errore durante connessione lato server");
            System.exit(1);
        }
    }

    public static void main(String[] args){
        Server tcpServer = new Server();
        tcpServer.start();
    }
}