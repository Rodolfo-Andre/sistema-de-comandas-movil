package com.example.project_kotlin.vistas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.project_kotlin.R
import com.example.project_kotlin.vistas.mesas.DatosMesas

class IndexComandasActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var cvConfig : CardView
    lateinit var cvCajaRegis : CardView
    lateinit var cvPedidos : CardView
    lateinit var cvReportes: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.index_comandas)
        cvConfig = findViewById(R.id.cvConfiguracion)
        cvCajaRegis = findViewById(R.id.cvCajaRegistradora)
        cvPedidos = findViewById(R.id.cvPedidos)
        cvReportes = findViewById(R.id.cvReportes)

        cvConfig.setOnClickListener({vincularConfig()})
        cvCajaRegis.setOnClickListener({vincularCaja()})
        cvPedidos.setOnClickListener({vincularPedidos()})
        cvReportes.setOnClickListener({vincularReportes()})

    }
    fun vincularConfig(){
        var intent = Intent(this, ConfiguracionVista::class.java)
        startActivity(intent)
    }

    fun vincularCaja(){
        var intent = Intent(this, CajaVista::class.java)
        startActivity(intent)
    }

    //PEDIDOS FALTA
    fun vincularPedidos(){
        var intent = Intent(this, PedidosVista::class.java)
        startActivity(intent)
    }

    //REPORTE VISTA FALTA
    fun vincularReportes(){
        var intent = Intent(this, ReporteVista::class.java)
        startActivity(intent)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}