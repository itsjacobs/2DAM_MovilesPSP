package org.example;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CuentaBancariaLock implements ICuentaBancaria{

    private CuentaBancaria cuentaBancaria;
    Lock lock = new ReentrantLock();
    
    public CuentaBancariaLock() {
        this.cuentaBancaria = new CuentaBancaria();
    }

    @Override
    public boolean retiroDinero(double monto) {
        lock.lock();
        try {
            if (monto <= cuentaBancaria.getSaldo()) {
                cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() - monto);
                int sumaNueva = cuentaBancaria.getTransaccionesExitosas() + 1;
                cuentaBancaria.setTransaccionesExitosas(sumaNueva);
                cuentaBancaria.getHistorialTransacciones().add("Retiro de: " + monto);
                return true;
            } else {
                int sumaNueva = cuentaBancaria.getTransaccionesFallidas() + 1;
                cuentaBancaria.setTransaccionesFallidas(sumaNueva);
                cuentaBancaria.getHistorialTransacciones().add("Fallo en retiro de: " + monto);
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void ingresoDinero(double monto) {
        lock.lock();
        try{
            cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() + monto);
            int sumaNueva = cuentaBancaria.getTransaccionesExitosas() + 1;
            cuentaBancaria.setTransaccionesExitosas(sumaNueva);
            cuentaBancaria.getHistorialTransacciones().add("Ingreso de: " + monto);
        } finally{
            lock.unlock();
        }
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
