/**
 *
 * @author studente
 */

import java.io.*;
import java.net.*;

public class CalcServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Server partito in esecuzione...");
        
        ServerSocket serversocket = new ServerSocket(5678);
        Socket socket = serversocket.accept();
        
        
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        double operando1, operando2;
        
        double risultato = 0;

        byte opCode;
        
        operando1 = dis.readDouble();
        opCode = dis.readByte();
        String s = String.valueOf(opCode);
        operando2 = dis.readDouble();
        
        switch (s) {
            case "43":
                risultato = operando1 + operando2;
                break;
            case "45":
                risultato = operando1 - operando2;
                break;
            case "47":
                risultato = operando1 / operando2;
                break;
            case "42":
                risultato = operando1 * operando2;
                break;
            default:
                break;
        }
        
        dos.writeDouble(risultato);
        dos.flush();
        
        socket.close();
        System.out.println("Server chiuso");
        
    }

}