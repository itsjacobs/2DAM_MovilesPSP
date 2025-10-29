package org.httpsproyecto1.domain.model;


import lombok.Data;

@Data
public class Partida {
    String nombreJugador;
    boolean ganado;
    int numeroSecreto;

}
