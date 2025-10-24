package com.example.proyecto1.ui.listadoSerie

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto1.R
import com.example.proyecto1.databinding.ActivityListadoSeriesBinding
import com.example.proyecto1.domain.modelo.Serie
import com.example.proyecto1.ui.pantallaDetalles.DetalleMainActivity
import com.example.proyecto1.ui.pantallamain.MainActivity

class ListadoSeriesActivity : ComponentActivity() {
    private val viewModel: ListadoSeriesViewModel by viewModels()
    private lateinit var binding : ActivityListadoSeriesBinding
    private lateinit var adapter : SeriesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityListadoSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SeriesAdapter(
            object : SeriesAdapterActions {
                override fun onClickItem(serie: Serie) {
                    irDetalleSerie(serie.titulo)
                }
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.state.observe(this) { state ->
            adapter.submitList(state.series)
        }
        binding.AddButton.setOnClickListener {
            val intent = Intent(this@ListadoSeriesActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun irDetalleSerie(Titulo : String){
        val intent = Intent(this@ListadoSeriesActivity, DetalleMainActivity::class.java)
        intent.putExtra("Titulo", Titulo)
        startActivity(intent)
    }



}