import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket mioSocket;
    BufferedReader tastiera;            //buffer per l'input da tastiera
    String stringaUtente;               //stringa inserita dall'utente
    String stringaRicevutaDalServer;    //stringa ricevuta dal server
    DataOutputStream outVersoServer;    //stream di output
    BufferedReader inDalServer;         //stream di input

    public Socket connetti(){
        System.out.println("2 CLIENT in esecuzione ...");
        try{
            //input da tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            mioSocket = new Socket(nomeServer,portaServer);

            //effettuare la scrittura e la lettura da server
            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
        }catch (UnknownHostException e){
            System.out.println("Host sconosciuto");
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return mioSocket;
    }

    public void comunica(){
        for(;;) //ciclo infinito che termina con "FINE"
            try{
                //leggo una riga
                System.out.println("4 inserisci la stringa da trasmettere al server: ");
                stringaUtente = tastiera.readLine();
                //la trasmetto al server
                System.out.println("5 invio la stringa al server");
                outVersoServer.writeBytes(stringaUtente + '\n');
                stringaRicevutaDalServer=inDalServer.readLine();
                System.out.println("7 - risposta dal server: " + stringaRicevutaDalServer);
                if(stringaUtente.equals("FINE")){
                    System.out.println("8 CLIENT: termina connessione");
                    mioSocket.close();
                    break;
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione al server!");
                System.exit(1);
            }
    }

    public static void main(String args[]){
        Client client = new Client();
        client.connetti();
        client.comunica();
    }
}