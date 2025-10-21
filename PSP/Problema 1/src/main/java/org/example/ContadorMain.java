package org.example;

public class ContadorMain {
    public static void main(String[] args) {


        IContadorVisitas contador = new ContSinSyn();
        ContadorServicios servicios = new ContadorServicios(contador);
        servicios.work();

        contador = new ContConSyn();
        servicios = new ContadorServicios(contador);
        servicios.work();

        contador = new ContAtomic();
        servicios = new ContadorServicios(contador);
        servicios.work();

    }
}
