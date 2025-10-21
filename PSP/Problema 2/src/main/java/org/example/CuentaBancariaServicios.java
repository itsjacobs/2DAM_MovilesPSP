package org.example;

import java.util.Random;

public class CuentaBancariaServicios {
    private ICuentaBancaria cuentaBancaria;

    public CuentaBancariaServicios(ICuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
    public ICuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }
    public void setCuentaBancaria(ICuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public void work(){
        Random rnd = new Random();
        Thread [] hilos = new Thread[50];
        for (int i = 0; i < 50; i++) {
            Thread hilo = new Thread(() -> {
                try {
                    Thread.sleep(rnd.nextInt(100, 300));
                } catch (InterruptedException e) {
                    System.out.println("El hilo se ha interrumpido");
                }
                int dado = rnd.nextInt(1, 10);
                if (dado <= 4) {
                    cuentaBancaria.ingresoDinero(rnd.nextDouble(1, 50));

                } else {
                    cuentaBancaria.retiroDinero(rnd.nextDouble(1, 100));
                }
            });
            hilos[i] = hilo;
            hilo.start();
        }
        for (int i = 0; i < 50; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println("El hilo se ha interrumpido");
            }
        }
        System.out.println("Saldo final: " + cuentaBancaria.getSaldoActual());
        System.out.println("Transacciones exitosas: " + cuentaBancaria.TransaccionesExitosas());
        System.out.println("Transacciones fallidas: " + cuentaBancaria.TransaccionesFallidas());
        System.out.println("Historial de transacciones: " + cuentaBancaria.historialTransacciones());


    }
}
