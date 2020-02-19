
package com.mycompany.banco;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    
    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream( clientSocket.getInputStream());
            out =new DataOutputStream( clientSocket.getOutputStream());
            this.start();
        } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }
    public void run(){
        try {			                 
            String data = in.readUTF();	 
            String [] datos=data.split(",");
            int resultado=0;
            int op1=Integer.parseInt(datos[0]);

            //case opcion:
            
            
            out.writeUTF(String.valueOf(resultado));
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}
