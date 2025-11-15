package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListResenya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationhiltroom.databinding.FragmentResenyaDetailBinding
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResenyaListFragment : Fragment() {
    private var _binding : FragmentResenyaDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResenyaListViewmodel by viewModels()
    private val args: ResenyaListFragmentArgs by navArgs()
    private lateinit var adapter: ResenyaListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResenyaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloSerie = args.tituloSerie

        binding.tvTituloSerie.text = Constantes.RESEÑA + tituloSerie

        viewModel.cargarResenyas(tituloSerie)

        setupRecyclerView()
        setupBotones()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ResenyaListAdapter { resenya ->
            findNavController().navigate(
                ResenyaListFragmentDirections.actionResenyaListFragmentToResenyaIndividualFragment(resenya.id)
            )
        }

        binding.recyclerViewResenyas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ResenyaListFragment.adapter
        }
    }

    private fun setupBotones() {
        binding.fabAddResenya.setOnClickListener {
            findNavController().navigate(
                ResenyaListFragmentDirections.actionResenyaListFragmentToResenyaAddFragment(args.tituloSerie)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.resenyas)

            if (state.resenyas.isEmpty()) {
                binding.EmptyMessage.visibility = View.VISIBLE
                binding.EmptyMessage.text = Constantes.ERRORCARGARRESEÑA
            } else {
                binding.EmptyMessage.visibility = View.GONE
            }

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
                if (event.route.contains(Constantes.RUTALISTADO, ignoreCase = true)) {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarResenyas(args.tituloSerie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}