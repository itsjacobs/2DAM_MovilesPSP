package com.example.proyecto1.ui.pantallaDetalles

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto1.R
import com.example.proyecto1.databinding.ActivityDetalleMainBinding
import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.modelo.Tipo
import com.example.proyecto1.domain.usecases.ActualizarSerieUsecase
import com.example.proyecto1.domain.usecases.DeleteSerieUsecase
import com.example.proyecto1.domain.usecases.GetSeriesUseCase
import com.example.proyecto1.ui.commons.UIEvent
import com.example.proyecto1.ui.listadoSerie.ListadoSeriesActivity
import com.google.android.material.snackbar.Snackbar

class DetalleMainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetalleMainBinding
    private val viewModel: DetalleSerieViewModel by viewModels{
        DetalleViewModelFactory(
            GetSeriesUseCase(),
            DeleteSerieUsecase(),
            ActualizarSerieUsecase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetalleMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val titulo = intent.getStringExtra("Titulo") ?: ""
            viewModel.getSeries(titulo)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eventos()
        observer()
    }

    private fun observer(){
        viewModel.uiState.observe(this) { state ->
            state?.let {
                it.event?.let { event ->
                    when(event){
                        is UIEvent.showSnackbar -> {
                            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                        }
                    }
                    viewModel.limpiarEvento()
                }
                if (it.event == null){
                    with(binding) {
                        tituloSerie.setText(it.serie.titulo)
                        genero.setText(it.serie.textoGenero)
                        temporadas.setText(it.serie.numeroTemporadas.toString())
                        anioEstreno.setText(it.serie.anoEstreno.toString())
                        ultimaEmision.setText(it.serie.ultimaEmision)
                        sinopsis.setText(it.serie.textoSinopsis)
                        terminos.isChecked = it.serie.isAceptado

                        when(it.serie.estadoSerie) {
                            Tipo.EnEmision -> enEmision.isChecked = true
                            Tipo.Finalizada -> finalizada.isChecked = true
                            Tipo.Proximamtente -> proximamente.isChecked = true
                            else -> {}
                        }
                    }
                }
            }
        }
    }
    private fun eventos(){
        with(binding){
            btnBorrar.setOnClickListener {
                viewModel.uiState.value?.serie?.let { serie ->
                    viewModel.clickBorrar(serie)
                    val intent = Intent(this@DetalleMainActivity, ListadoSeriesActivity::class.java)
                    startActivity(intent)
                }
            }
            btnActualizar.setOnClickListener {
                val tituloActualizado = tituloSerie.text.toString()
                val generoActualizado = genero.text.toString()
                val temporadasActualizadas = temporadas.text.toString().toIntOrNull() ?: 0
                val anioEstrenoActualizado = anioEstreno.text.toString().toIntOrNull() ?: 0
                val ultimaEmisionActualizada = ultimaEmision.text.toString()
                val sinopsisActualizada = sinopsis.text.toString()
                val terminosAceptados = terminos.isChecked

                val estadoActualizado = when {
                    enEmision.isChecked -> Tipo.EnEmision
                    finalizada.isChecked -> Tipo.Finalizada
                    proximamente.isChecked -> Tipo.Proximamtente
                    else -> Tipo.VACIO
                }

                val serieActualizada = Serie(
                    titulo = tituloActualizado,
                    textoGenero = generoActualizado,
                    numeroTemporadas = temporadasActualizadas,
                    anoEstreno = anioEstrenoActualizado,
                    ultimaEmision = ultimaEmisionActualizada,
                    textoSinopsis = sinopsisActualizada,
                    isAceptado = terminosAceptados,
                    estadoSerie = estadoActualizado
                )

                viewModel.clickActualizarSerie(serieActualizada)
                val intent = Intent(this@DetalleMainActivity, ListadoSeriesActivity::class.java)
                startActivity(intent)
            }
            btnVolver.setOnClickListener {
                val intent = Intent(this@DetalleMainActivity, ListadoSeriesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}