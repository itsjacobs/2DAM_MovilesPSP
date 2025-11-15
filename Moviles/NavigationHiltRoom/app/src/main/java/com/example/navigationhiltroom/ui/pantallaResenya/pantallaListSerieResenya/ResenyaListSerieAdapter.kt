package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListSerieResenya

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationhiltroom.databinding.ItemSerieBinding
import com.example.navigationhiltroom.domain.modelo.Serie

class ResenyaListSerieAdapter(
    private val onSerieClick: (Serie) -> Unit
) : ListAdapter<Serie, ResenyaListSerieAdapter.SerieViewHolder>(SerieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val binding = ItemSerieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SerieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        holder.bind(getItem(position), onSerieClick)
    }

    class SerieViewHolder(
        private val binding: ItemSerieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: Serie, onSerieClick: (Serie) -> Unit) {
            binding.tvTitulo.text = serie.titulo
            binding.numTemporadas.text = "Temporadas: ${serie.numeroTemporadas}"

            binding.btnFavorite.visibility = android.view.View.GONE

            binding.root.setOnClickListener {
                onSerieClick(serie)
            }
        }
    }

    class SerieDiffCallback : DiffUtil.ItemCallback<Serie>() {
        override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean {
            return oldItem.titulo == newItem.titulo
        }

        override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean {
            return oldItem == newItem
        }
    }
}
