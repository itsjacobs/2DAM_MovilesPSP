package org.example;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pedido {
    private final String nombre;
    private final TipoPlato tipoPlato;
    private final int tiempoPreparacion;
    private final int id;

    public Pedido(TipoPlato tipoPlato, int id) {
        this.tipoPlato = tipoPlato;
        this.nombre = id +"-"+tipoPlato.name();
        this.tiempoPreparacion = tipoPlato.getTiempoMs();
        this.id = id;

    }

    @Override
    public String toString() {
        return tipoPlato.toString();
    }

}
