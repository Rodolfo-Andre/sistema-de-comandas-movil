package com.example.project_kotlin.adaptador.vistas

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class VistaItemAgregarMesa(itemView: View):RecyclerView.ViewHolder(itemView) {

    var tvCanAsientos: TextView
    var tvEstadoMesa:TextView

    init {
        tvCanAsientos = itemView.findViewById(R.id.tvCanAsientos)
        tvEstadoMesa = itemView.findViewById(R.id.tvEstadoMesa)
    }

}