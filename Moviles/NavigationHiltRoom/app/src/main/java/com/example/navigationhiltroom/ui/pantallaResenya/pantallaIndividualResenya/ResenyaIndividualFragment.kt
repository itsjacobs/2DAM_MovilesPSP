package com.example.navigationhiltroom.ui.pantallaResenya.pantallaIndividualResenya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationhiltroom.R
import com.example.navigationhiltroom.databinding.FragmentResenyaIndividualBinding
import com.example.navigationhiltroom.domain.modelo.Calificacion
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResenyaIndividualFragment : Fragment() {
    private var _binding : FragmentResenyaIndividualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResenyaIndividualViewModel by viewModels()
    private val args: ResenyaIndividualFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResenyaIndividualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cargarResenya(args.resenyaId)

        setupBotones()
        observeViewModel()
    }

    private fun setupBotones() {
        binding.btnActualizar.setOnClickListener {
            val contenido = binding.etContenido.text.toString()
            val calificacion = when (binding.radioGroupCalificacion.checkedRadioButtonId) {
                R.id.rbUnaEstrella -> Calificacion.UNA_ESTRELLA
                R.id.rbDosEstrellas -> Calificacion.DOS_ESTRELLAS
                R.id.rbTresEstrellas -> Calificacion.TRES_ESTRELLAS
                R.id.rbCuatroEstrellas -> Calificacion.CUATRO_ESTRELLAS
                R.id.rbCincoEstrellas -> Calificacion.CINCO_ESTRELLAS
                else -> Calificacion.VACIO
            }

            viewModel.updateResenya(contenido, calificacion)
        }

        binding.btnBorrar.setOnClickListener {
            viewModel.deleteResenya()
        }

        binding.btnVolver.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.resenya?.let { resenya ->
                binding.SerieTitulo.text = Constantes.SERIE + resenya.tituloSerie
                binding.etContenido.setText(resenya.contenido)

                val radioButtonId = when (resenya.calificacion) {
                    Calificacion.UNA_ESTRELLA -> R.id.rbUnaEstrella
                    Calificacion.DOS_ESTRELLAS -> R.id.rbDosEstrellas
                    Calificacion.TRES_ESTRELLAS -> R.id.rbTresEstrellas
                    Calificacion.CUATRO_ESTRELLAS -> R.id.rbCuatroEstrellas
                    Calificacion.CINCO_ESTRELLAS -> R.id.rbCincoEstrellas
                    Calificacion.VACIO -> -1
                }
                binding.radioGroupCalificacion.check(radioButtonId)
            }

            state.event?.let { event ->
                manejarEventos(event)
            }
        }
    }

    private fun manejarEventos(event: UIEvent) {
        when (event) {
            is UIEvent.showSnackbar -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                viewModel.limpiarEventos()
            }
            is UIEvent.Navigate -> {
                viewModel.limpiarEventos()
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
