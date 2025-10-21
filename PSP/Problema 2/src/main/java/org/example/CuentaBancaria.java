package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CuentaBancaria{
    private double saldo = 10000;
    private int TransaccionesExitosas = 0;
    private int TransaccionesFallidas = 0;
    private List<String> historialTransacciones = new ArrayList<>();

    public CuentaBancaria() {
    }
}
