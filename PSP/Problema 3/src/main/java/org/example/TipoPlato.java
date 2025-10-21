package org.example;

public enum TipoPlato  {
        ENSALADA(2000),    // 2 segundos
        PASTA(3000),       // 3 segundos
        PIZZA(4000),       // 4 segundos
        CARNE(5000);// 5 segundos
    private final int tiempoMs;

    TipoPlato(int tiempoMs) {
        this.tiempoMs = tiempoMs;
    }

    public int getTiempoMs() {
        return tiempoMs;
    }
}

