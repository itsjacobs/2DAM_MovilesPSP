package com.example.navigationhiltroom.ui.pantallaSerie.pantallaListadoSerie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationhiltroom.databinding.FragmentSerieListBinding
import com.example.navigationhiltroom.domain.modelo.Serie
import com.example.navigationhiltroom.ui.commons.Constantes
import com.example.navigationhiltroom.ui.commons.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SerieListFragment : Fragment(), SeriesAdapterActions {

    private var _binding : FragmentSerieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListadoSeriesViewModel by viewModels()
    private lateinit var adapter: SeriesAdapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSerieListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        botonAdd()
        botonFavoritos()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = SeriesAdapter(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SerieListFragment.adapter
        }
    }

    private fun botonAdd() {
        binding.AddButton.setOnClickListener {
            findNavController().navigate(
                SerieListFragmentDirections.actionSerieListFragmentToSerieAddFragment()
            )
        }
    }

    private fun botonFavoritos() {
        binding.btnFavoritos.setOnClickListener {
            findNavController().navigate(
                SerieListFragmentDirections.actionSerieListFragmentToSerieFavoritas()
            )
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

    override fun onClickItem(serie: Serie) {
        findNavController().navigate(
            SerieListFragmentDirections.actionSerieListFragmentToSerieDetailFragment(serie.titulo)
        )
    }

    override fun onFavoriteClick(serie: Serie) {
        viewModel.updateFavorito(serie.titulo, !serie.isFavorito)
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