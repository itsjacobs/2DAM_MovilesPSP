package org.example;

import java.util.Random;

public class ContadorServicios{
    private IContadorVisitas contador;

    public ContadorServicios(IContadorVisitas contador) {
        this.contador = contador;
    }

    public IContadorVisitas getContador(){
        return contador;
    }
    public void setContador(IContadorVisitas contador){
        this.contador = contador;
    }
    public void work(){
        Random rnd = new Random();
        Thread[] hilos = new Thread[1000];

        for (int i = 0; i < 1000; i++) {
            Thread hilo = new Thread(() -> {
                try {
                    Thread.sleep(rnd.nextInt(50, 150));
                } catch (InterruptedException e) {
                    System.out.println("El hilo se ha interrumpido");
                }
                contador.incrementarVisitas();
            });
            hilos[i] = hilo;
            hilo.start();
        }
        for (int i = 0; i < 1000; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println("El hilo se ha interrumpido");
            }
        }
        System.out.println("Contador de visitas: " + contador.getContador());
    }

}

