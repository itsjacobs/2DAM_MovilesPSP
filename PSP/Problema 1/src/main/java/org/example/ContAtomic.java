package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class ContAtomic implements IContadorVisitas {

    AtomicInteger contador = new AtomicInteger(0);

    public void incrementarVisitas(){
        contador.incrementAndGet();
    }
    public int getContador(){
        return contador.get();
    }
}
