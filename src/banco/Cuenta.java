
package banco;

import java.util.ArrayList;

public class Cuenta {
    public static ArrayList<Cuenta> Cuentas = new ArrayList<Cuenta>();
    private int numeroCuenta;
    private int saldo;
    private boolean bloqueado;
    
    public Cuenta(int numeroCuenta, int saldo){
        this.numeroCuenta=numeroCuenta;
        this.saldo=saldo;
        this.bloqueado=false;   //Por defecto las cuentas están desbloqueadas
    }
    
//Get & Set
    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    
}
