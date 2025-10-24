package com.example.proyecto1.data

import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.modelo.Tipo

object RepositorioSeries {

    private val series = mutableListOf<Serie>()

    init{
        series.addAll(
            listOf(
                Serie(
                    titulo = "Breaking Bad",
                    textoGenero = "Crimen / Drama",
                    numeroTemporadas = 5,
                    anoEstreno = 2008,
                    ultimaEmision = "2013-09-29",
                    textoSinopsis = "Un profesor de química convertido en fabricante de metanfetamina.",
                    isAceptado = true,
                    estadoSerie = Tipo.Finalizada
                ),
                Serie(
                    titulo = "Stranger Things",
                    textoGenero = "Ciencia ficción / Suspense",
                    numeroTemporadas = 4,
                    anoEstreno = 2016,
                    ultimaEmision = "2022-07-01",
                    textoSinopsis = "Niños enfrentan sucesos sobrenaturales en su pueblo.",
                    isAceptado = true,
                    estadoSerie = Tipo.EnEmision
                ),
                Serie(
                    titulo = "Game of Thrones",
                    textoGenero = "Fantasía / Drama",
                    numeroTemporadas = 8,
                    anoEstreno = 2011,
                    ultimaEmision = "2019-05-19",
                    textoSinopsis = "Nobles luchan por el control de los siete reinos.",
                    isAceptado = true,
                    estadoSerie = Tipo.Finalizada
                ),
                Serie(
                    titulo = "The Crown",
                    textoGenero = "Histórica / Drama",
                    numeroTemporadas = 6,
                    anoEstreno = 2016,
                    ultimaEmision = "2023-11-16",
                    textoSinopsis = "La vida y reinado de la reina Isabel II.",
                    isAceptado = false,
                    estadoSerie = Tipo.EnEmision
                ),
                Serie(
                    titulo = "The Mandalorian",
                    textoGenero = "Aventura / Ciencia ficción",
                    numeroTemporadas = 3,
                    anoEstreno = 2019,
                    ultimaEmision = "2023-03-01",
                    textoSinopsis = "Cazarrecompensas en el universo Star Wars.",
                    isAceptado = true,
                    estadoSerie = Tipo.EnEmision
                ),
                Serie(
                    titulo = "Chernobyl",
                    textoGenero = "Histórica / Drama",
                    numeroTemporadas = 1,
                    anoEstreno = 2019,
                    ultimaEmision = "2019-06-03",
                    textoSinopsis = "Recreación del desastre nuclear de Chernóbil.",
                    isAceptado = true,
                    estadoSerie = Tipo.Finalizada
                ),
                Serie(
                    titulo = "Dark",
                    textoGenero = "Ciencia ficción / Thriller",
                    numeroTemporadas = 3,
                    anoEstreno = 2017,
                    ultimaEmision = "2020-06-27",
                    textoSinopsis = "Viajes en el tiempo que destapan secretos familiares.",
                    isAceptado = true,
                    estadoSerie = Tipo.Finalizada
                ),
                Serie(
                    titulo = "The Witcher",
                    textoGenero = "Fantasía / Acción",
                    numeroTemporadas = 2,
                    anoEstreno = 2019,
                    ultimaEmision = "2023-06-29",
                    textoSinopsis = "Cazador de monstruos en un mundo oscuro y mágico.",
                    isAceptado = false,
                    estadoSerie = Tipo.EnEmision
                ),
                Serie(
                    titulo = "House of the Dragon",
                    textoGenero = "Fantasía / Drama",
                    numeroTemporadas = 2,
                    anoEstreno = 2022,
                    ultimaEmision = "2024-10-XX",
                    textoSinopsis = "Historia del linaje Targaryen y la guerra civil.",
                    isAceptado = true,
                    estadoSerie = Tipo.EnEmision
                ),
                Serie(
                    titulo = "Foundation",
                    textoGenero = "Ciencia ficción / Épica",
                    numeroTemporadas = 2,
                    anoEstreno = 2021,
                    ultimaEmision = "2024-04-XX",
                    textoSinopsis = "Adaptación de la saga de Isaac Asimov sobre el futuro galáctico.",
                    isAceptado = false,
                    estadoSerie = Tipo.Proximamtente
                )
            )
        )
    }

    fun getSerie(titulo : String) = series.find { serie -> serie.titulo == titulo } ?: Serie()
    fun save(serie: Serie) = series.add(serie)
    fun size() = series.size
    fun delete(serie: Serie)= series.remove(serie)
    fun update(serieActualizada : Serie) : Boolean{
        val indice = series.indexOfFirst { serieDelaLista -> serieDelaLista.titulo == serieActualizada.titulo }
        return if (indice != -1) {
            series[indice] = serieActualizada
            true
        } else {
            false
        }
    }

    fun getSeries(): List<Serie> = series.toList()
}