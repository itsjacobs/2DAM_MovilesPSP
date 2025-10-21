package org.example;

import java.util.List;

public interface ICuentaBancaria {
    public boolean retiroDinero(double monto);
    public void ingresoDinero(double monto);
    public double getSaldoActual();
    public List<String> historialTransacciones();
    public int TransaccionesExitosas();
    public int TransaccionesFallidas();
}
