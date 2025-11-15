package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListSerieResenya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationhiltroom.databinding.FragmentResenyaListBinding
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResenyaListSerieFragment : Fragment() {
    private var _binding : FragmentResenyaListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResenyaListSerieViewModel by viewModels()
    private lateinit var adapter: ResenyaListSerieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResenyaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ResenyaListSerieAdapter { serie ->
            findNavController().navigate(
                ResenyaListSerieFragmentDirections.actionResenyaListSerieFragmentToResenyaListFragment(serie.titulo)
            )
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ResenyaListSerieFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.series)

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
        viewModel.recargarSeries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
