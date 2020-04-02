
package banco;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        } catch(IOException e) {System.out.println("Connection:"+e.toString());}
    }
    @Override
    public void run(){
        try {			       
            //Funcion encargada de trafico de datos con cada cajero
            Banco.procesarDato(in,out);
        } catch(Exception e) {if(e.getMessage()!=null)System.out.println("ExceptError:"+e.getLocalizedMessage());
            //Se cierra la conexion
        } finally{ try {clientSocket.close();}catch (IOException e){}}
    }
}
