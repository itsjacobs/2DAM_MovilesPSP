package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
public class Cocinero implements Runnable{
    private final List<Pedido> listaPedidos;
    private final BlockingQueue<Pedido> colaPedidos;
    private AtomicInteger contadorPedidos;
    private volatile boolean running = true;


    public Cocinero(List<Pedido> listaPedidos, BlockingQueue<Pedido> colaPedidos, AtomicInteger contadorPedidos){
        this.listaPedidos = listaPedidos;
        this.colaPedidos = colaPedidos;
        this.contadorPedidos = contadorPedidos;
        running = true;
    }

    @Override
    public void run() {
        try{
            while(running){
                Pedido pedido = colaPedidos.take();
                Thread.sleep(pedido.getTipoPlato().getTiempoMs());
                contadorPedidos.incrementAndGet();
                listaPedidos.add(pedido);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
