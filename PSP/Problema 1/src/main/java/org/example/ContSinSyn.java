package org.example;

public class ContSinSyn implements IContadorVisitas {
    private int contador;

    public void incrementarVisitas(){
        int sumaNueva = contador + 1;
        contador = sumaNueva;
    }
    public int getContador(){
        return contador;
    }
}
