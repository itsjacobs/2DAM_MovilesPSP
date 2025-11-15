package com.example.navigationhiltroom.ui.pantallaResenya.pantallaListResenya

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationhiltroom.databinding.ItemResenyaBinding
import com.example.navigationhiltroom.domain.modelo.Resenya

class ResenyaListAdapter(
    private val onResenyaClick: (Resenya) -> Unit
) : ListAdapter<Resenya, ResenyaListAdapter.ResenyaViewHolder>(ResenyaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResenyaViewHolder {
        val binding = ItemResenyaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResenyaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResenyaViewHolder, position: Int) {
        holder.bind(getItem(position), onResenyaClick)
    }

    class ResenyaViewHolder(
        private val binding: ItemResenyaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(resenya: Resenya, onResenyaClick: (Resenya) -> Unit) {
            binding.tvContenido.text = resenya.contenido
            binding.tvCalificacion.text = "Calificación: ${resenya.calificacion.name.replace("_", " ")}"

            binding.root.setOnClickListener {
                onResenyaClick(resenya)
            }
        }
    }

    class ResenyaDiffCallback : DiffUtil.ItemCallback<Resenya>() {
        override fun areItemsTheSame(oldItem: Resenya, newItem: Resenya): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Resenya, newItem: Resenya): Boolean {
            return oldItem == newItem
        }
    }
}
