package com.example.project_kotlin.vistas.caja_registradora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R

class CajaVista : AppCompatActivity(){

    private lateinit var btnBuscar: Button

    private lateinit var spnCaja: Spinner
    private lateinit var edtFechaEmision: EditText
    private lateinit var edtPrecioInicial: EditText
    private lateinit var edtPrecioFin: EditText
    private lateinit var spnMetPago: Spinner

    private lateinit var listPagos:RecyclerView
    private lateinit var edtVentaTotal:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.caja_activity)

        btnBuscar = findViewById(R.id.btnBuscar)
        spnCaja = findViewById(R.id.spnCaja)
        edtFechaEmision = findViewById(R.id.edtFechaEmision)
        edtPrecioInicial = findViewById(R.id.edtPrecioInicial)
        edtPrecioFin = findViewById(R.id.edtPrecioFin)
        spnMetPago = findViewById(R.id.spnMetPago)
        listPagos = findViewById(R.id.listPagos)
        edtVentaTotal = findViewById(R.id.edtVentaTotal)


    }


}