package com.example.proyecto1.ui.listadoSerie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto1.databinding.ItemSerieBinding
import com.example.proyecto1.domain.modelo.Serie

class SeriesAdapter (
    val actions : SeriesAdapterActions
) : ListAdapter<Serie, SeriesViewHolder>(SerieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = ItemSerieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeriesViewHolder(binding, actions)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SerieDiffCallback : DiffUtil.ItemCallback<Serie>() {
    override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean {
        return oldItem.textoTitulo == newItem.textoTitulo && oldItem.numeroTemporadas == newItem.numeroTemporadas
    }

    override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean {
        return oldItem == newItem
    }
}

class SeriesViewHolder(
    private val binding : ItemSerieBinding,
    private val actions: SeriesAdapterActions
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(serie: Serie) {
        binding.tvTitulo.text = serie.textoTitulo
        binding.numTemporadas.text = "Temporadas: ${serie.numeroTemporadas}"

        binding.root.setOnClickListener {
            actions.onClickItem(serie)
        }
    }
}

interface SeriesAdapterActions {
    fun onClickItem(serie: Serie)
}