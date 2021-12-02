import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {
    private int nUtenti=0;

    public void nuovoUtente(){
        nUtenti++;
    }

    public void uscitaUtente(){
        nUtenti--;
    }

    public int utentiConnessi(){
        return nUtenti;
    }

    public void azzeraUtenti(){
        nUtenti=0;
    }

    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(6789);
            Server tcpServer = new Server();
            for(;;){
                System.out.println("1 Server in attesa...");
                Socket socket = serverSocket.accept();
                System.out.println("3 Server socket "+socket);
                Globali.clientConnessi += 1;
                //tcpServer.nuovoUtente();
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
                Thread.sleep(5000);
                System.out.println("Utenti connessi: " + Globali.clientConnessi);
                
                if(Globali.clientConnessi<4){
                    Thread.sleep(60000);
                    tcpServer.eseguiEstrazione();
                    for(int i=0; i<Globali.numeriVincenti.length; i++){
                        System.out.print(Globali.numeriVincenti[i] + " ");
                    }
                    System.out.println();
                    
                }
            }
        }catch(Exception e){
            System.out.println("Errore durante connessione server");
        }
    }

    public void eseguiEstrazione(){

        Random r = new Random();
        for(int i=0; i<Globali.numeriVincenti.length; i++){
            Globali.numeriVincenti[i] = r.nextInt(90);
        }
    }
    
    public static void main(String[] args) {
        Server tcpServer = new Server();

        tcpServer.start();
        
    }
}