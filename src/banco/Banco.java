
package banco;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Banco {
    public static void main(String args[]){
        lecturaFichero();
        guardarFichero();
        try{
            int serverPort = 8888; 
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
    public static boolean lecturaFichero(){
        File fichero = new File("Cuentas.db");
	Scanner s = null;
        try {
            // Leemos el contenido del fichero
            s = new Scanner(fichero);

            // Leemos linea a linea el fichero
            while (s.hasNextLine()) {
                // Guardamos la linea en un String
                String linea = s.nextLine();
                String [] datos=linea.split(";");
                // Cargamos los datos en memoria
                addCuenta(Integer.valueOf(datos[0]),Integer.valueOf(datos[1]));
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
                System.out.println("Error: " + ex.getMessage());
        } finally {
            // Cerramos el fichero tanto si la lectura ha sido correcta o no
            try {
                if (s != null)
                    s.close();
            } catch (Exception ex2) {
                System.out.println("Error: " + ex2.getMessage());
            }
        }
        return true;
    }
    public static boolean guardarFichero(){
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("Cuentas2.db");
            for(int i=0;i<Cuenta.Cuentas.size();i++){
                // Escribimos linea a linea en el fichero
                fichero.write(Cuenta.Cuentas.get(i).getNumeroCuenta()+";"+Cuenta.Cuentas.get(i).getSaldo()+"\n");
            }
            fichero.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
            return false;
        }
    }
    
    public static boolean addCuenta(int numeroCuenta,int saldo){
        Cuenta.Cuentas.add(new Cuenta(numeroCuenta, saldo));
        return true;
    }
    public static Cuenta buscarCuenta(int numeroCuenta){
        for (int i=0;i<Cuenta.Cuentas.size();i++){
            if(Cuenta.Cuentas.get(i).getNumeroCuenta()==numeroCuenta){
                return Cuenta.Cuentas.get(i);
            }
        }
        return null;
    }
    public static void procesarDato(DataInputStream in, DataOutputStream out) throws IOException{
        String[] peticion;
        ArrayList<String> respuesta=null;
        int tarea;
        String error="";
        String respuestaUTF="";
        //Menu en bucle, hasta recibir 0->Salida
        do{
            peticion=in.readUTF().split(";");
            tarea=Integer.valueOf(peticion[0]);
            Cuenta cCliente=buscarCuenta(Integer.valueOf(peticion[1]));
            if(cCliente!=null){ //Existe la cuenta
                switch(tarea){
                    case 1: //lee_saldo
                        respuesta.add(String.valueOf(cCliente.getSaldo()));
                        break;
                    case 2: //Escribe saldo
                        cCliente.setSaldo(Integer.valueOf(peticion[2]));
                        break;
                    case 3: //Bloquea_cuenta
                        if(cCliente.isBloqueado()){
                            error="La cuenta esta bloqueada";
                        }else{
                            cCliente.setBloqueado(true); 
                        }
                        break;
                    case 4: //Desbloquea Cuenta
                        if(cCliente.isBloqueado()){
                            cCliente.setBloqueado(false);
                        }else{
                            error="La cuenta esta desbloqueada";
                        }          
                        break;
                    case 5: //Termina servicio
                        break;
                    default:
                        error="Operacion no valida";
                        break;
                }                
            }else{
                error="No existe la cuenta"; //No existe
            }           
        }while(tarea!=5);
        if(error.equals("")){   //Si no hay error = 0
            respuesta.add("0");
        }else{                  //Si hay error = 1
            respuesta.add("1");
        }
        for(int i=0;i<respuesta.size();i++){
            respuestaUTF+=";"+respuesta.get(i);
        }
        out.writeUTF(respuestaUTF);
    }
}
