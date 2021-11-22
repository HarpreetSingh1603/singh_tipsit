package app;

import java.io.*;
import java.net.*;

public class ServerUppercase
{
    ServerSocket server;
    Socket client;
    String stringaRicevuta;
    String stringaModificata;
    BufferedReader inDalClient;
    BufferedWriter outVersoClient;

    public void connettiAlClient()
    {
        try
        {
            System.out.println("1 SERVER partito in esecuzione. In attesa del client ...");
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
        try
        {
            // rimango in attesa della riga trasnmessa dal client
            System.out.println("3 benvenuto client, scrivi una frase e la trasformo in maiuscolo. Attendo ...");
            stringaRicevuta = inDalClient.readLine();
            System.out.println("6 ricevuta la stringa dal cliente : "+stringaRicevuta);

            //la modifico e la rispedisco al client
            stringaModificata=stringaRicevuta.toUpperCase();
            System.out.println("7 invio la stringa modificata al client ...");
            outVersoClient.write(stringaModificata + System.lineSeparator()); //"\r\n" in Windows, "\n" in Linux...
            outVersoClient.flush();
            //termina elaborazione sul server : chiudo la connessione del client
            System.out.println("9 SERVER: fine elaborazione  ... buona notte!");
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
        ServerUppercase serverUppercase = new ServerUppercase();
        serverUppercase.connettiAlClient();
        serverUppercase.serviClient();
    }
}