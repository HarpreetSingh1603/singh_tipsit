import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{
    ServerSocket server;
    Socket client;
    String stringaRicevuta;
    String stringaModificata;
    BufferedReader inDalClient;
    BufferedReader textFile;
    BufferedWriter outVersoClient;
    HashMap<String, String> rubrica = new HashMap<String, String>();
    
    public void connettiAlClient()
    {
        try
        {
            System.out.println("SERVER partito in esecuzione. In attesa del client ...");
            // creo un server sulla porta 6789
            server = new ServerSocket(6789);
            // rimane in attesa di un client
            client = server.accept();
            // chiudo il server per inibire altri client
            server.close();
            //associo due oggetti al socket del client per effettuare la scrittura e la lettura
            inDalClient = new BufferedReader(new InputStreamReader (client.getInputStream()));
            outVersoClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server !");
            System.exit(1);
        }
    }

    public void serviClient()
    {
        try {
            textFile = new BufferedReader(new FileReader("text.txt"));
            
            String tmp = " ";
            while(tmp != null){
                //tmp = textFile.readLine();
                rubrica.put(tmp = textFile.readLine(), tmp = textFile.readLine());
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*rubrica.put("mario", "3209564120");
        rubrica.put("antonio", "3209553211");
        rubrica.put("giulia", "3395422613");*/
        try
        {
            // rimango in attesa della riga trasnmessa dal client
            System.out.println("benvenuto client, scrivi un nome e ritorno il suo numero di telefono. Attendo ...");
            stringaRicevuta = inDalClient.readLine();
            stringaRicevuta = stringaRicevuta.toLowerCase();
            try
            {
                stringaModificata = rubrica.get(stringaRicevuta);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("Nome inesistente");
                System.exit(2);
            }
            //la modifico e la rispedisco al client
            System.out.println("invio il numero di telefono al client ...");
            outVersoClient.write(stringaModificata + System.lineSeparator()); //"\r\n" in Windows, "\n" in Linux...
            outVersoClient.flush();
            //termina elaborazione sul server : chiudo la connessione del client
            client.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione...");
            System.exit(2);
        }
    }

    public static void main(String args[]) {
        Server server = new Server();
        server.connettiAlClient();
        server.serviClient();
    }
}
