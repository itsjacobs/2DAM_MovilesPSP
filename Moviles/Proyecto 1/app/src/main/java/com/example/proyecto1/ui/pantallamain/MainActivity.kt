package com.example.proyecto1.ui.pantallamain

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto1.R
import com.example.proyecto1.databinding.ActivityMainBinding
import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.domain.modelo.Tipo

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel: MainViewModel by viewModels{
        MainViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        eventos()
        observer()

    }
    private fun observer(){
        viewModel.state.observe(this){
           state ->
            binding.btnSiguiente.isEnabled = state.botonSiguiente
            binding.btnAnterior.isEnabled = state.botonAnterior
            binding.numPaginas.setText("Página ${state.Indice} / ${state.numeroPaginas}")
            binding.tituloSerie.setText(state.serie.textoTitulo)
            binding.genero.setText(state.serie.textoGenero)
            binding.temporadas.setText(state.serie.numeroTemporadas.toString())
            binding.anioEstreno.setText(state.serie.anoEstreno.toString())
            binding.ultimaEmision.setText(state.serie.ultimaEmision)
            if (state.serie.estadoSerie == Tipo.EnEmision){
                binding.estadoSerie.check(R.id.enEmision)
            }else if(state.serie.estadoSerie == Tipo.Finalizada){
                binding.estadoSerie.check(R.id.finalizada)
            }else{
                binding.estadoSerie.check(R.id.proximamente)
            }
            binding.sinopsis.setText(state.serie.textoSinopsis)
            binding.terminos.isChecked = state.serie.isAceptado

            state.mensaje?.let {
                mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                viewModel.limpiarMensaje()
            }
        }
    }

    fun eventos(){
        binding.guardar.setOnClickListener {
            val tipo : Tipo
            if(binding.estadoSerie.checkedRadioButtonId == R.id.enEmision){
                tipo = Tipo.EnEmision
            }else if(binding.estadoSerie.checkedRadioButtonId == R.id.finalizada) {
                tipo = Tipo.Finalizada
            }else{
                tipo = Tipo.Proximamtente
            }
            val serie = createSerie(tipo)
            viewModel.clickGuardarSerie(serie)
        }
        binding.btnSiguiente.setOnClickListener {
            viewModel.pasarPagina()
        }
        binding.btnAnterior.setOnClickListener {
            viewModel.volverPagina()
        }
        binding.limpiar.setOnClickListener {
            viewModel.clickLimpiar()
        }
        binding.borrar.setOnClickListener {
            val tipo : Tipo
            if(binding.estadoSerie.checkedRadioButtonId == R.id.enEmision){
                tipo = Tipo.EnEmision
            }else if(binding.estadoSerie.checkedRadioButtonId == R.id.finalizada) {
                tipo = Tipo.Finalizada
            }else{
                tipo = Tipo.Proximamtente
            }
            val serie = createSerie(tipo)
            viewModel.clickBorrar(serie)
        }
        binding.actualizar.setOnClickListener {
            val tipo : Tipo
            if(binding.estadoSerie.checkedRadioButtonId == R.id.enEmision){
                tipo = Tipo.EnEmision
            }else if(binding.estadoSerie.checkedRadioButtonId == R.id.finalizada) {
                tipo = Tipo.Finalizada
            }else{
                tipo = Tipo.Proximamtente
            }
            val serie = createSerie(tipo)
            viewModel.clickActualizarSerie(serie)
        }
    }

    private fun createSerie(tipo: Tipo): Serie {
        val serie = Serie(
            binding.tituloSerie.text.toString(),
            binding.genero.text.toString(),
            binding.temporadas.text.toString().toInt(),
            binding.anioEstreno.text.toString().toInt(),
            binding.ultimaEmision.text.toString(),
            binding.sinopsis.text.toString(),
            binding.terminos.isChecked,
            tipo
        )
        return serie
    }
}