package org.httpsproyecto1.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Estadisticas {
    private List<Partida> partidas = new ArrayList<Partida>();
}
