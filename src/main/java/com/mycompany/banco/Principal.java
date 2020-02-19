
package com.mycompany.banco;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Principal {
    public static void main(String args[]){
        try{
            int serverPort = 8888; 
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c;
                c = new Connection(clientSocket) {};
            }
        } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
    //Leer Saldo
    //Escribir Saldo
    //Bloquear Cuenta
    //Desbloquear Cuenta
    //Terminar Servicio
}
