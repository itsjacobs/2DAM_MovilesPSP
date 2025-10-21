package org.example;


import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Vehiculo implements Runnable {

    private final int id;
    private final TiposVehiculos tipoVehiculo;
    private final BlockingQueue<Vehiculo> colaVehiculos;
    private AtomicInteger vehiculosExito;
    private AtomicInteger vehiculosFallido;
    private final int tiempo;
    private volatile boolean running = true;

    public Vehiculo(int id, TiposVehiculos tipoVehiculo, BlockingQueue<Vehiculo> colaVehiculos, AtomicInteger vehiculosExito, AtomicInteger vehiculosFallido, int tiempo) {
        this.id = id;
        this.tipoVehiculo = tipoVehiculo;
        this.colaVehiculos = colaVehiculos;
        this.vehiculosExito = vehiculosExito;
        this.vehiculosFallido = vehiculosFallido;
        this.tiempo = tiempo;
        running = true;
    }

    @Override
    public void run() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        boolean enCola = colaVehiculos.offer(this);

        if (enCola) {
            System.out.println("[" + LocalTime.now().format(formatter) + "] Coche-" + id + " ha entrado en la cola.");
        } else {
            System.out.println("[" + LocalTime.now().format(formatter) + "] Coche-" + id + " se ha ido. La cola está llena.");
            vehiculosFallido.incrementAndGet();
            running = false;
        }
    }
}
