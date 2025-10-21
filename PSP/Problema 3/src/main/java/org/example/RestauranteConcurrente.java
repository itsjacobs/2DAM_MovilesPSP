package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RestauranteConcurrente {
    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random();
        long startTime = System.currentTimeMillis();
        System.out.println("=== RESTAURANTE CONCURRENTE ===");
        System.out.println("Iniciando servicio con 3 cocineros...\n");
        BlockingQueue<Pedido> colaPedidos = new LinkedBlockingQueue<>(10);
        AtomicInteger contadorPedidos = new AtomicInteger(0);
        AtomicInteger contadorMesasLlenas = new AtomicInteger(0);
        List<Pedido> listaPedidos = new ArrayList<>();
        List<Thread> cocineros = new ArrayList<>();
        List<Thread> clientes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Thread cocinero = new Thread(new Cocinero(listaPedidos,colaPedidos,contadorPedidos));
            cocinero.start();
            cocineros.add(cocinero);
        }

        for (int i = 0; i < 100; i++) {
            Thread cliente = new Thread(new Cliente(colaPedidos,listaPedidos,"Cliente " +(i+1),i+1,contadorMesasLlenas));
            cliente.start();
            clientes.add(cliente);
            try{
                Thread.sleep(rnd.nextInt(0,500));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        for (Thread cliente: clientes){
            cliente.join();
        }

        for (Thread cocinero : cocineros){
            cocinero.interrupt();
        }
        for (Thread cocinero : cocineros){
            cocinero.join();
        }
        System.out.println("\nFin del servicio");
        System.out.println("Pedidos realizados: " + contadorPedidos.get());
        System.out.println("Tiempo promedio de espera: " + (System.currentTimeMillis() - startTime) / 1000);
        System.out.println("Mesa llena (veces): " + contadorMesasLlenas.get());
        System.out.println("Eficiencia:" + contadorPedidos.get()*100/100 + "%");


    }
}
