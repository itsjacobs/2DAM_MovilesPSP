package org.example;

import java.util.List;

public class CuentaBancariaSyn implements ICuentaBancaria {
    private CuentaBancaria cuentaBancaria;
    
    public CuentaBancariaSyn() {
        this.cuentaBancaria = new CuentaBancaria();
    }

    @Override
    public synchronized boolean retiroDinero(double monto) {
        boolean a = false;
        if (monto <= cuentaBancaria.getSaldo()) {
            cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() - monto);
            int sumaNueva = cuentaBancaria.getTransaccionesExitosas() + 1;
            cuentaBancaria.setTransaccionesExitosas(sumaNueva);
            cuentaBancaria.getHistorialTransacciones().add("Retiro de: " + monto);
            a = true;
        } else {
            int sumaNueva = cuentaBancaria.getTransaccionesFallidas() + 1;
            cuentaBancaria.setTransaccionesFallidas(sumaNueva);
            cuentaBancaria.getHistorialTransacciones().add("Fallo en retiro de: " + monto);
        }
        return a;
    }

    @Override
    public synchronized void ingresoDinero(double monto) {
        cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() + monto);
        int sumaNueva = cuentaBancaria.getTransaccionesExitosas() + 1;
        cuentaBancaria.setTransaccionesExitosas(sumaNueva);
        cuentaBancaria.getHistorialTransacciones().add("Ingreso de: " + monto);
    }

    @Override
    public double getSaldoActual() {
        return cuentaBancaria.getSaldo();
    }

    @Override
    public List<String> historialTransacciones() {
        return cuentaBancaria.getHistorialTransacciones();
    }

    @Override
    public int TransaccionesExitosas() {
        return cuentaBancaria.getTransaccionesExitosas();
    }

    @Override
    public int TransaccionesFallidas() {
        return cuentaBancaria.getTransaccionesFallidas();
    }

}
