package com.example.project_kotlin.vistas.comandas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.vistas.empleados.DatosEmpleados
import com.example.project_kotlin.vistas.inicio.IndexComandasActivity

class ComandasVista:AppCompatActivity() {


    private lateinit var spnEstadoComanda : Spinner
    private lateinit var rvComanda : RecyclerView
    private lateinit var btnNuevaComanda : Button
    private lateinit var btnVolverConfi : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.man_comanda)
        spnEstadoComanda = findViewById(R.id.spnEstadoComanda)
        rvComanda = findViewById(R.id.rvComanda)
        btnNuevaComanda = findViewById(R.id.btnNuevaComanda)
        btnVolverConfi = findViewById(R.id.btnVolverConfi)


        btnVolverConfi.setOnClickListener({volver()})
        btnNuevaComanda.setOnClickListener({nuevaComanda()})


    }

    fun nuevaComanda(){
        var intent = Intent(this, EditarComanda::class.java)
        startActivity(intent)
    }

    fun volver(){
        var intent = Intent(this, IndexComandasActivity::class.java)
        startActivity(intent)
    }

}