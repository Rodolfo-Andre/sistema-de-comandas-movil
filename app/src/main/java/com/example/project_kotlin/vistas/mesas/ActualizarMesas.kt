package com.example.project_kotlin.vistas.mesas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.project_kotlin.R
import com.example.project_kotlin.dao.ComandaDao
import com.example.project_kotlin.dao.MesaDao
import com.example.project_kotlin.db.ComandaDatabase
import com.example.project_kotlin.entidades.Mesa
import com.example.project_kotlin.utils.appConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActualizarMesas : AppCompatActivity() {

    private lateinit var edCantAsientos: EditText
    private lateinit var edNumMesa: EditText
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnVolver: Button
    private lateinit var mesaDao: MesaDao
    private lateinit var comandaDao: ComandaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_mesa)
        mesaDao = appConfig.db.mesaDao()
        comandaDao = appConfig.db.comandaDao()

        edCantAsientos = findViewById(R.id.edtCanModMesa)
        edNumMesa = findViewById(R.id.edtNumMesa)
        btnEditar = findViewById(R.id.btnEditarMesa)
        btnEliminar = findViewById(R.id.btnEliminarMesa)
        btnVolver = findViewById(R.id.btnVolverListadoEdit)

        btnVolver.setOnClickListener { Volver() }
        btnEliminar.setOnClickListener { Eliminar() }
        btnEditar.setOnClickListener { Editar() }

        //Cargar dato
        val mesa = intent.getSerializableExtra("mesa") as Mesa
        edNumMesa.setText(mesa.id.toString())
        edCantAsientos.setText(mesa.cantidadAsientos.toString())
    }

    fun Volver() {
        val intent = Intent(this, DatosMesas::class.java)
        startActivity(intent)
    }

    fun Eliminar() {
        val numMesa = edNumMesa.text.toString().toInt()
        val mensaje: AlertDialog.Builder = AlertDialog.Builder(this)
        mensaje.setTitle("Sistema comandas")
        mensaje.setMessage("¿Seguro de eliminar?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton("Aceptar") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                //Validar de comandas
                val validarComandaPorMesa = comandaDao.obtenerComandasPorMesa(numMesa)
                if (validarComandaPorMesa.isEmpty()) {
                    val eliminar = mesaDao.obtenerPorId(numMesa.toLong())
                    mesaDao.eliminar(eliminar)
                    mostrarToast("Mesa eliminada correctamente")
                    Volver()
                } else {
                    mostrarToast("No puedes eliminar mesas que tienen información de comandas")
                }
            }
        }
        mensaje.setNegativeButton("Cancelar") { _, _ -> }
        mensaje.setIcon(android.R.drawable.ic_delete)
        mensaje.show()
    }

    fun Editar() {
        val numMesa = edNumMesa.text.toString().toLong()
        lifecycleScope.launch(Dispatchers.IO) {
            val validar = mesaDao.obtenerPorId(numMesa)
            if (validar.estadoMesa == "Libre") {
                if (validarCampos()) {
                    val cantidadAsientos = edCantAsientos.text.toString().toInt()
                    val mesa = Mesa(numMesa, cantidadAsientos, "Libre")
                    mesaDao.actualizar(mesa)
                    mostrarToast("Mesa actualizada correctamente")
                    Volver()
                }
            } else {
                mostrarToast("No puedes actualizar una mesa ocupada")
            }
        }
    }

    fun validarCampos(): Boolean {
        val cantidad = edCantAsientos.text.toString().toIntOrNull()
        if (cantidad == null || cantidad !in 1..9) {
            mostrarToast("La cantidad de asientos debe ser un número del 1 al 9")
            return false
        }
        return true
    }

    private fun mostrarToast(mensaje: String) {
        runOnUiThread {
            Toast.makeText(appConfig.CONTEXT, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}