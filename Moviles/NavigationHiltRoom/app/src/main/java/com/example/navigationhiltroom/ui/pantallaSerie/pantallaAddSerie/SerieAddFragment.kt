package com.example.navigationhiltroom.ui.pantallaSerie.pantallaAddSerie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.navigationhiltroom.databinding.FragmentSerieAddBinding
import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.domain.modelo.Tipo
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SerieAddFragment : Fragment() {
    private var _binding : FragmentSerieAddBinding? = null
    private val binding get() = _binding!!


    private val viewModel: AddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSerieAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBotones()
        observeViewModel()
    }

    private fun setupBotones() {
        binding.btnVolver.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnGuardar.setOnClickListener {
            val serie = addSerie()

            if (serie.titulo.isBlank()) {
                Snackbar.make(binding.root, Constantes.ERRORTITULO, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.clickGuardarSerie(serie)
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.event?.let { event ->
                manejarEventos(event)
                viewModel.limpiarEvento()
            }
        }
    }

    private fun addSerie(): Serie {
        binding.apply {
            val titulo = tituloSerie.text.toString().trim()
            val generoText = genero.text.toString().trim()
            val numTemporadas = temporadas.text.toString().toIntOrNull() ?: 0
            val anio = anioEstreno.text.toString().toIntOrNull() ?: 0
            val emision = ultimaEmision.text.toString().trim()
            val sinopsisText = sinopsis.text.toString().trim()
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
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
            }
            is UIEvent.Navigate -> {
                if (event.route == Constantes.RUTALISTADO) {
                    Toast.makeText(requireContext(), Constantes.SERIEADD, Toast.LENGTH_SHORT).show()
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