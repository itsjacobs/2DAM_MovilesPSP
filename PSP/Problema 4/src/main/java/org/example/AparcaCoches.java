package org.example;

import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class AparcaCoches implements Runnable {

    private final BlockingQueue<Vehiculo> colaVehiculos;
    private final Semaphore plazasNormales;
    private final Semaphore plazasVip;
    private AtomicInteger ingresos;
    private AtomicInteger contador;
    private volatile boolean running = true;

    public AparcaCoches(BlockingQueue<Vehiculo> colaVehiculos, Semaphore plazasNormales, Semaphore plazasVip, AtomicInteger ingresos) {
        this.colaVehiculos = colaVehiculos;
        this.plazasNormales = plazasNormales;
        this.plazasVip = plazasVip;
        this.ingresos = ingresos;
        this.contador = new AtomicInteger(0);
        running = true;
    }

    private void mostrarEstadisticas() {
        int usadasNormales = 20 - plazasNormales.availablePermits();
        int usadasVIP = 5 - plazasVip.availablePermits();
        int enCola = colaVehiculos.size();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("\n=== ESTADÍSTICAS DEL PARKING ===");
        System.out.println("[" + LocalTime.now().format(formatter) + "]");
        System.out.printf("Plazas normales : %2d/20%n", usadasNormales);
        System.out.printf("Plazas VIP      : %2d/5%n", usadasVIP);
        System.out.printf("Cola de espera  : %2d/10%n", enCola);
        System.out.printf("Ingresos totales: %3d€%n", ingresos.get());
        System.out.println("===============================");
    }
    
    private void entrada(Semaphore semaforoActual,DateTimeFormatter formatter,Vehiculo vehiculo) {
        System.out.println("[" + LocalTime.now().format(formatter) + "] " + "coche-" + vehiculo.getId() +" "+ vehiculo.getTipoVehiculo() + " ha aparcado en la plaza " + semaforoActual.availablePermits() + ".");

        new Thread(() -> {
            try {
                contador.incrementAndGet();
                Thread.sleep(2000 + vehiculo.getTiempo() + 1000);

                int pago = (int) (vehiculo.getTipoVehiculo().getTarifaPorMinuto() * vehiculo.getTiempo() / 1000);
                ingresos.addAndGet(pago);

                System.out.println("[" + LocalTime.now().format(formatter) + "] " + "coche-" + vehiculo.getId() +" " +vehiculo.getTipoVehiculo() + " ha salido de la plaza " + semaforoActual.availablePermits() + " y ha pagado " + pago + "€.");
                semaforoActual.release();
                synchronized (AparcaCoches.this) {
                    AparcaCoches.this.notify();
                }
                contador.decrementAndGet();
                mostrarEstadisticas();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        while(running){
            try {
                Vehiculo vehiculo = colaVehiculos.take();
                boolean acquire = false;
                Semaphore semaforoActual = null;

                if (vehiculo.getTipoVehiculo() == TiposVehiculos.VIP) {
                    if(acquire = plazasVip.tryAcquire()){
                        semaforoActual = plazasVip;
                    }else if(acquire = plazasNormales.tryAcquire()){
                        semaforoActual = plazasNormales;
                    }
                } else {
                    acquire = plazasNormales.tryAcquire();
                    semaforoActual = plazasNormales;
                }
                if (acquire) {
                    mostrarEstadisticas();
                    entrada(semaforoActual,formatter,vehiculo);
                    vehiculo.getVehiculosExito().incrementAndGet();
                }

                if(plazasNormales.availablePermits() == 0 && plazasVip.availablePermits() == 0){
                    synchronized (this) {
                        this.wait();
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }
    

}