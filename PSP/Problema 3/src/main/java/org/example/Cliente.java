package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
public class Cliente implements Runnable{
    private final BlockingQueue<Pedido> colaPedidos;
    private final List<Pedido> listaPedidos;
    private final String nombre;
    private final int id;
    private volatile boolean running = true;
    private AtomicInteger contadorMesasLlenas;

    public Cliente(BlockingQueue<Pedido> colaPedidos, List<Pedido> listaPedidos, String nombre, int id, AtomicInteger contadorMesasLlenas){
        this.colaPedidos = colaPedidos;
        this.listaPedidos = listaPedidos;
        this.nombre = nombre;
        this.id = id;
        this.contadorMesasLlenas = contadorMesasLlenas;
        running = true;
    }

    @Override
    public void run() {
        TipoPlato tipo = TipoPlato.values()[ThreadLocalRandom.current().nextInt(TipoPlato.values().length)];
        Pedido pedido = new Pedido(tipo, id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        boolean pedidosubido = colaPedidos.offer(pedido);

        if (pedidosubido){
            System.out.println(" [" + LocalTime.now().format(formatter) + "]" + nombre + " ha encargado un pedido: " + pedido.getNombre());
            listaPedidos.add(pedido);
            while (running) {
                synchronized (listaPedidos) {
                    Pedido recibido = listaPedidos.stream().filter(p -> p.getId() == pedido.getId()).findFirst().orElse(null);
                    if (recibido != null) {
                        System.out.println(" [" + LocalTime.now().format(formatter) + "]" + nombre + " ha recibido su pedido: " + recibido.getNombre());
                        listaPedidos.remove(recibido);
                        running = false;
                        break;
                    }
                }
            }
        }else{
            running = false;
            contadorMesasLlenas.incrementAndGet();
        }

    }
}
