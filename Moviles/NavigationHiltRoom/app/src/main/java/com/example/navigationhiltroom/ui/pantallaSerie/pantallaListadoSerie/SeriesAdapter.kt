package com.example.navigationhiltroom.ui.pantallaSerie.pantallaListadoSerie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationhiltroom.databinding.ItemSerieBinding
import com.example.navigationhiltroom.domain.modelo.Serie


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
        return oldItem.titulo == newItem.titulo && oldItem.numeroTemporadas == newItem.numeroTemporadas
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
        binding.tvTitulo.text = serie.titulo
        binding.numTemporadas.text = "Temporadas: ${serie.numeroTemporadas}"

        updateFavoriteIcon(serie.isFavorito)

        binding.root.setOnClickListener {
            actions.onClickItem(serie)
        }

        binding.btnFavorite.setOnClickListener {
            actions.onFavoriteClick(serie)
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnFavorite.setImageResource(android.R.drawable.star_big_off)
        }
    }
}

interface SeriesAdapterActions {
    fun onClickItem(serie: Serie)
    fun onFavoriteClick(serie: Serie)
}