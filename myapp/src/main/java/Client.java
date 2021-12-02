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
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    
    int[] numeriAcquistati;

    public Socket connetti() {

        System.out.println("2 CLIENT in esecuzione");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            mioSocket = new Socket(nomeServer, portaServer);
            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Host sconosciuto");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return mioSocket;
    }

    public void comunica() {
        try {

            System.out.println("4 inserisci quanti numeri vuoi acquistare ");
            stringaUtente = tastiera.readLine();
            System.out.println("5 invio quantità numeri al server");
            outVersoServer.writeBytes(stringaUtente + '\n');
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println("7 numeri acquistati: " + stringaRicevutaDalServer);

            Thread.sleep(5000);
            int numeriVincenti = ControllaNumeriVincenti(stringaRicevutaDalServer, Integer.parseInt(stringaUtente));
            System.out.println("Numeri vincenti: " + numeriVincenti);
            mioSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione con il server");
            System.exit(1);
        }
        
    }

    public int ControllaNumeriVincenti(String str, int nUtente) {
            int numeriVincenti=0;

                String[] vstr = str.split ("\\s+");
                int[] vint = new int[vstr.length];

                for (int i = 0; i < 5; i++)
                    vint[i] = Integer.parseInt (vstr[i]);




            for(int i=0; i<Globali.numeriVincenti.length; i++){
                for(int j=0;j<5; j++) {
                    if (Globali.numeriVincenti[i] == vint[j]) {
                        numeriVincenti++;
                    }
                }
            }
            return numeriVincenti;
    }

    
    public static void main(String[] args) {
        Client client = new Client();
        client.connetti();
        client.comunica();
    }
}