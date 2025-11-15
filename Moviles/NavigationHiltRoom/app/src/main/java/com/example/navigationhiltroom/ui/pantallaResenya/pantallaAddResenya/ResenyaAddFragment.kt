package com.example.navigationhiltroom.ui.pantallaResenya.pantallaAddResenya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationhiltroom.R
import com.example.navigationhiltroom.databinding.FragmentResenyaAddBinding
import com.example.navigationhiltroom.domain.modelo.Calificacion
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResenyaAddFragment : Fragment() {
    private var _binding : FragmentResenyaAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResenyaAddViewmodel by viewModels()
    private val args: ResenyaAddFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResenyaAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cargarTituloSerie(args.tituloSerie)

        setupButtons()
        observeViewModel()
    }

    private fun setupButtons() {
        binding.btnGuardar.setOnClickListener {
            val contenido = binding.etContenido.text.toString()
            val calificacion = when (binding.radioGroupCalificacion.checkedRadioButtonId) {
                R.id.rbUnaEstrella -> Calificacion.UNA_ESTRELLA
                R.id.rbDosEstrellas -> Calificacion.DOS_ESTRELLAS
                R.id.rbTresEstrellas -> Calificacion.TRES_ESTRELLAS
                R.id.rbCuatroEstrellas -> Calificacion.CUATRO_ESTRELLAS
                R.id.rbCincoEstrellas -> Calificacion.CINCO_ESTRELLAS
                else -> Calificacion.VACIO
            }

            viewModel.addResenya(contenido, calificacion)
        }

        binding.btnCancelar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.SerieTitulo.text =  Constantes.SERIE + state.tituloSerie

            state.event?.let { event ->
                manejarEventos(event)
                viewModel.limpiarEvento()
            }
        }
    }

    private fun manejarEventos(event: UIEvent) {
        when (event) {
            is UIEvent.showSnackbar -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
            }
            is UIEvent.Navigate -> {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}