package org.example;



public enum TiposVehiculos {
    Normal(1.0),
    VIP(2.0);

    private final Double tarifaPorMinuto;
    TiposVehiculos(Double tarifaPorMinuto) {
        this.tarifaPorMinuto = tarifaPorMinuto;
    }
    public Double getTarifaPorMinuto() {
        return tarifaPorMinuto;
    }
}
