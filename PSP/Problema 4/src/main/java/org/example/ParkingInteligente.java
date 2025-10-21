package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingInteligente {

    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random();
        Semaphore plazasNormales = new Semaphore(20, true);
        Semaphore plazasVip = new Semaphore(5, true);
        BlockingQueue<Vehiculo> colaVehiculos = new LinkedBlockingQueue<>(10);
        AtomicInteger vehiculosExito = new AtomicInteger(0);
        AtomicInteger vehiculosFallido = new AtomicInteger(0);
        AtomicInteger ingresos = new AtomicInteger(0);

        List<Thread> vehiculos = new ArrayList<>();
        List<Integer> tiempos = new ArrayList<>();
        double tiempoPromedio = 0;

        AparcaCoches aparcaCoches = new AparcaCoches(colaVehiculos, plazasNormales, plazasVip, ingresos);
        Thread aparcoches = new Thread(aparcaCoches);
        aparcoches.start();

        for (int i = 1; i <= 30; i++) {
            TiposVehiculos tipo = null;
            int tiempo = rnd.nextInt(10000,30000);
            int random = rnd.nextInt(1,10);
            if (random <= 5){
                tipo = TiposVehiculos.Normal;
            }else{
                tipo = TiposVehiculos.VIP;
            }
            Thread vehiculo = new Thread(new Vehiculo(i, tipo, colaVehiculos, vehiculosExito, vehiculosFallido, tiempo));

            vehiculo.start();
            vehiculos.add(vehiculo);
            tiempos.add(tiempo);

            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        for(Thread vehiculo : vehiculos){
            vehiculo.join();
        }

        aparcoches.interrupt();

        while(!colaVehiculos.isEmpty() || aparcaCoches.getContador().get() > 0){
            Thread.sleep(500);
        }

        for (int tiempo : tiempos) {
            tiempoPromedio += tiempo;
        }

        System.out.println("\n=== RESUMEN DEL DÍA ===");
        System.out.println("Vehiculos totales: " + vehiculos.size());
        System.out.println("Vehículos aparcados con éxito: " + vehiculosExito.get() + "/" + vehiculos.size());
        System.out.println("Vehículos que se marcharon (cola llena): " + vehiculosFallido.get() + "/" + vehiculos.size());
        System.out.printf("Tiempo promedio de estancia: %.2f ms%n", (tiempoPromedio / vehiculos.size()) / 1000.0);
        System.out.println("Ingresos totales: " + ingresos.get() + "€");
    }
}

