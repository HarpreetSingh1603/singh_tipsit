/**
 *
 * @author studente
 */

import java.io.*;
import java.net.*;

public class CalcServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(5678);
        Socket socket = serversocket.accept();

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        double operando1, operando2;
        double risultato = 0;

        byte opCode;
        
        operando1 = dis.readDouble();
        opCode = dis.readByte();
        operando2 = dis.readDouble();
        
        if(opCode == OpCode.ADD){
            risultato = operando1 + operando2;
        } else if(opCode == OpCode.SUB){
            risultato = operando1 - operando2;
        } else if(opCode == OpCode.DIV){
            risultato = operando1 / operando2;
        } else if(opCode == OpCode.MUL){
            risultato = operando1 * operando2;
        }
        
        dos.writeDouble(risultato);
        
        socket.close();
        
    }

}