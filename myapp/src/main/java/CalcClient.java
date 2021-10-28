/**
 *
 * @author studente
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class CalcClient {
    
    public static void main(String[] args) throws IOException{
        
        InetAddress ip = InetAddress.getLocalHost();
        int port = 5678;
        Scanner sc = new Scanner(System.in);
        

        Socket socket = null;
        
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Impossibile connetersi al server.");
            System.exit(-1);
        }
        
        DataInputStream dis =  new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        
        double operando1, operando2, risultato;
        byte opCode;
        String operazione;
        
        System.out.println("Inserisci un'operazione tra due numeri: ");
        operazione = sc.nextLine();
        //StringTokenizer token = new StringTokenizer(operazione, "  ");
        String[] op = operazione.split(" ");
        
        for(String a: op)
            System.out.println(a);
        
        operando1 = Double.parseDouble(op[0]);
        opCode = Byte.parseByte(op[1]);
        operando2 = Double.parseDouble(op[2]);
        
        System.out.println(operando1);
        System.out.println(opCode);
        System.out.println(operando2);
        
        dos.writeDouble(operando1);
        dos.writeByte(opCode);
        dos.writeDouble(operando2);
        
        System.out.println(dis.readDouble());
        
        socket.close();
    }
    
}