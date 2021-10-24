import java.io.*;
import java.net.*;

public class Client {
    Socket miosocket;
    BufferedReader tastiera;            // buffer per l'input da tastiera
    String stringaUtente;               // stringa inserita da utente
    String stringaRicevutaDalServer;    // stringa ricevuta dal server
    BufferedWriter outVersoServer;      // stream di output
    BufferedReader inDalServer;         // stream di input

    public Socket connettiAlServer(){
        System.out.println("CLIENT partito in esecuzione. Connessione al server ...");
        try
        {
            // creo un socket
            miosocket = new Socket("localhost",6789);

            // adatto i due canali di I/O associati alla socket per la scrittura e la lettura di linee di testo
            outVersoServer = new BufferedWriter(new OutputStreamWriter(miosocket.getOutputStream()));
            inDalServer    = new BufferedReader(new InputStreamReader (miosocket.getInputStream()));
        }
        catch (UnknownHostException e){
            System.err.println("Host sconosciuto");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return miosocket;
    }

    public void comunicaConSever() {
        try                      // leggo una riga
        {
            System.out.println("inserisci la stringa da trasmettere al server:"+'\n');
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            stringaUtente = tastiera.readLine();
            // la spedisco al server
            System.out.println("invio la stringa al server e attendo ...");
            outVersoServer.write( stringaUtente + System.lineSeparator());  //"\r\n" in Windows, "\n" in Linux...
            outVersoServer.flush();
            // leggo la risposta dal server
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("risposta dal server: " + stringaRicevutaDalServer);
            // chiudo la connessione
            System.out.println("CLIENT: termina elaborazione e chiude connessione" );
            miosocket.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }
    }

    public static void main(String args[]) {
        Client cliente = new Client();
        cliente.connettiAlServer();
        cliente.comunicaConSever();
    }
}