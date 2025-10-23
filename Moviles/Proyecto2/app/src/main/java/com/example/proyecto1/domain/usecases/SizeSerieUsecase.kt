package com.example.proyecto1.domain.usecases

import com.example.proyecto1.data.RepositorioSeries

class SizeSerieUsecase {
    operator fun invoke() : Int = RepositorioSeries.size()
}