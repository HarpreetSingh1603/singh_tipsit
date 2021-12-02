import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;



public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaInviata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    Server utenti = new Server();
    
    public ServerThread(Socket client){
        this.client = client;
    }
    public void run(){
        try {
            comunica();
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }

    private void comunica() throws Exception {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());
        Random r = new Random();
        int nNumeri;
        int[] numeriAcquistati;
        
        stringaRicevuta = inDalClient.readLine();
        nNumeri = Integer.parseInt(stringaRicevuta);
        numeriAcquistati = new int[nNumeri];
        stringaInviata = "";
        for(int i=0; i<nNumeri; i++){

            numeriAcquistati[i] = r.nextInt(90);
            stringaInviata += numeriAcquistati[i] + " ";
        }

        outVersoClient.writeBytes(stringaInviata+"\n");
        System.out.println("6 Echo sul server: "+stringaInviata);
        Thread.sleep(10000);
        outVersoClient.close();
        inDalClient.close();

        System.out.println("9 Chiusura socket");
        utenti.uscitaUtente();
        client.close();
        Globali.clientConnessi -= 1;
        System.out.println("Utenti connessi: " + Globali.clientConnessi);

    }
}
