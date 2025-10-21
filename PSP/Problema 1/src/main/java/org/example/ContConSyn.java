package org.example;

public class ContConSyn implements IContadorVisitas {
    private int contador;

    public synchronized void incrementarVisitas(){
        int sumaNueva = contador + 1;
        contador = sumaNueva;
    }
    public int getContador(){
        return contador;
    }
}

