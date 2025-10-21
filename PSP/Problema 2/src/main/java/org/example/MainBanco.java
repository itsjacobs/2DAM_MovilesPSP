package org.example;

public class MainBanco {
    public static void main(String[] args) {

        System.out.println("=== Banco Virtual ===");
        System.out.println("Saldo Inicial: 10000");
        System.out.println("50 clientes accediendo a la cuenta bancaria");

        System.out.println("\n=== Sincronizado ===");
        ICuentaBancaria cuenta = new CuentaBancariaSyn();
        CuentaBancariaServicios servicios = new CuentaBancariaServicios(cuenta);
        servicios.work();

        System.out.println("\n === ReentrantLock ===");
        cuenta = new CuentaBancariaLock();
        servicios = new CuentaBancariaServicios(cuenta);
        servicios.work();
    }
}