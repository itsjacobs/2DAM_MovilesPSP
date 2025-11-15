package com.example.navigationhiltroom.ui.pantallaSerie.pantallaDetailSerie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationhiltroom.databinding.FragmentSerieDetailBinding
import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.domain.modelo.Tipo
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SerieDetailFragment : Fragment() {
    private var _binding : FragmentSerieDetailBinding? = null
    private val binding get() = _binding!!
    private val args: SerieDetailFragmentArgs by navArgs()
    private val viewModel : DetalleSerieViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSerieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSeries(args.titulo)
        setupBotones()
        observeViewModel()
    }

    private fun setupBotones() {
        binding.btnVolver.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnBorrar.setOnClickListener {
            val serie = AddSerie()
            viewModel.clickBorrar(serie)
        }

        binding.btnActualizar.setOnClickListener {
            val serie = AddSerie()
            viewModel.clickActualizarSerie(serie)
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            observarCamposSerie(state.serie)

            state.event?.let { event ->
                manejarEventos(event)
                viewModel.limpiarEvento()
            }
        }
    }

    private fun observarCamposSerie(serie: Serie) {
        binding.apply {
            tituloSerie.setText(serie.titulo)
            genero.setText(serie.textoGenero)
            temporadas.setText(serie.numeroTemporadas.toString())
            anioEstreno.setText(serie.anoEstreno.toString())
            ultimaEmision.setText(serie.ultimaEmision)
            sinopsis.setText(serie.textoSinopsis)
            terminos.isChecked = serie.isAceptado

            when (serie.estadoSerie) {
                Tipo.EnEmision -> enEmision.isChecked = true
                Tipo.Finalizada -> finalizada.isChecked = true
                Tipo.Proximamtente -> proximamente.isChecked = true
                else -> {}
            }
        }
    }

    private fun AddSerie(): Serie {
        binding.apply {
            val titulo = tituloSerie.text.toString()
            val generoText = genero.text.toString()
            val numTemporadas = temporadas.text.toString().toIntOrNull() ?: 0
            val anio = anioEstreno.text.toString().toIntOrNull() ?: 0
            val emision = ultimaEmision.text.toString()
            val sinopsisText = sinopsis.text.toString()
            val aceptado = terminos.isChecked

            val estado = when (estadoSerie.checkedRadioButtonId) {
                enEmision.id -> Tipo.EnEmision
                finalizada.id -> Tipo.Finalizada
                proximamente.id -> Tipo.Proximamtente
                else -> Tipo.VACIO
            }

            return Serie(
                titulo = titulo,
                textoGenero = generoText,
                numeroTemporadas = numTemporadas,
                anoEstreno = anio,
                ultimaEmision = emision,
                textoSinopsis = sinopsisText,
                isAceptado = aceptado,
                estadoSerie = estado
            )
        }
    }

    private fun manejarEventos(event: UIEvent) {
        when (event) {
            is UIEvent.showSnackbar -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
            }
            is UIEvent.Navigate -> {
                if (event.route.contains(Constantes.RUTALISTADO, ignoreCase = true)) {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}