
/**
 *
 * @author studente
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class CalcClient {

    public static void main(String[] args) throws IOException {

        InetAddress ip = InetAddress.getLocalHost();
        int port = 5678;
        Scanner sc = new Scanner(System.in);
        String input;

        Socket socket = null;

        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Impossibile connetersi al server.");
            System.exit(-1);
        }

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        double operando1 = 0, operando2 = 0, risultato;
        byte opCode = 0;

        System.out.println("Inserire: OPERANDO OPERATORE OPERANDO separati da spazi");
        System.out.println("Esempio: 1.4 + 1.56");
        System.out.println("Al posto del + si può mettere un operatore qualsiasi +,-,*,/");

        try {
            input = sc.nextLine();
            String[] dati = input.strip().split("\\s+");
            operando1 = Double.parseDouble(dati[0]);
            opCode = OpCode.getOpCodeForString(dati[1]);
            operando2 = Double.parseDouble(dati[2]);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {

        }
        dos.writeDouble(operando1);
        dos.writeByte(opCode);
        dos.writeDouble(operando2);

        risultato = dis.readDouble();
        
        

        while (opCode != OpCode.EXIT) {
            System.out.println("Risultato = " + risultato);
            try {
                input = sc.nextLine();
                String[] dati = input.strip().split("\\s+");
                operando1 = Double.parseDouble(dati[1]);
                opCode = OpCode.getOpCodeForString(dati[0]);
            } catch (IndexOutOfBoundsException | NumberFormatException e) {

            }
            dos.writeByte(opCode);
            dos.writeDouble(operando1);
            

            risultato = dis.readDouble();

        }
        
        socket.close();
    }

}
