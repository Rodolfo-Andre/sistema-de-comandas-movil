package com.example.project_kotlin.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.project_kotlin.dao.*
import com.example.project_kotlin.entidades.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(
    entities = [Caja::class,
        Cargo::class, Comanda::class,
        Comprobante::class, Usuario::class,
        CategoriaPlato::class, Mesa::class,
        DetalleComprobante::class, DetalleComanda::class,
        Empleado::class, Plato::class, Establecimiento::class,
        EstadoComanda::class, MetodoPago::class, TipoComprobante::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ComandaDatabase : RoomDatabase() {
    abstract fun cajaDao() : CajaDao
    abstract fun cargoDao() : CargoDao
    abstract fun comandaDao() : ComandaDao
    abstract fun comprobanteDao() : ComprobanteDao
    abstract fun usuarioDao() : UsuarioDao
    abstract fun categoriaPlatoDao() : CategoriaPlatoDao
    abstract fun mesaDao() : MesaDao

    //Entidades de Gary Morales
    abstract fun metodoPagoDao() : MetodoPagoDao
    abstract fun tipoComprobanteDao() : TipoComprobanteDao
    abstract fun estadoComandaDao(): EstadoComandaDao
    abstract fun establecimientoDao() : EstablecimientoDao
    abstract fun platoDao() : PlatoDao
    abstract fun empleadoDao() : EmpleadoDao
    abstract fun detalleComandaDao() : DetalleComandaDao
    abstract fun detalleComprobanteDao() : DetalleComprobanteDao

    companion object {
        @Volatile
        private var INSTANCIA: ComandaDatabase? = null

        fun obtenerBaseDatos(context: Context): ComandaDatabase {
            return INSTANCIA ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ComandaDatabase::class.java,
                    "comanda_database"
                ) .addCallback(object : RoomDatabase.Callback() {
                    @Override
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        CoroutineScope(Dispatchers.IO).launch() {
                            val instancia = obtenerBaseDatos(context)

                            //Al crear ingresar datos
                            val estadosComandaDao = instancia?.estadoComandaDao()
                            val metodosPagoDao = instancia?.metodoPagoDao()
                            val categoriaPlatoDao = instancia?.categoriaPlatoDao()
                            val establecimientoDao = instancia?.establecimientoDao()
                            val tipoComprobanteDao = instancia?.tipoComprobanteDao()
                            val empleadoDao = instancia?.empleadoDao()
                            val usuarioDao = instancia?.usuarioDao()
                            val cargoDao = obtenerBaseDatos(context).cargoDao()

                            //Agregando cargos
                            cargoDao.guardar(Cargo(cargo= "Administrador"))
                            cargoDao.guardar(Cargo(cargo = "Gerente"))
                            cargoDao.guardar(Cargo(cargo= "Mesero"))
                            cargoDao.guardar(Cargo(cargo= "Cajero"))
                            cargoDao.guardar(Cargo(cargo="Cocinero"))
                            //Agregando estados
                            estadosComandaDao?.guardar(EstadoComanda(estadoComanda ="Generada"))
                            estadosComandaDao?.guardar(EstadoComanda(estadoComanda= "Preparado"))
                            estadosComandaDao?.guardar(EstadoComanda(estadoComanda= "Pagada"))
                            //Métodos de pago
                            metodosPagoDao?.registrar(MetodoPago(nombreMetodoPago =  "Yape"))
                            metodosPagoDao?.registrar(MetodoPago( nombreMetodoPago = "BCP"))
                            //Categoría Plato
                            categoriaPlatoDao?.guardar(CategoriaPlato("C-001", "Entradas"))
                            //Establecimiento
                            establecimientoDao?.guardar(
                                Establecimiento(1, "Nombre",
                                    "966250432", "Dirección pruebas", "12345678910")
                            )
                            //Tipo comprobante
                            tipoComprobanteDao?.guardar(TipoComprobante(nombreComprobante = "Nota de Venta"))
                            tipoComprobanteDao?.guardar(TipoComprobante(nombreComprobante = "Boleta"))
                            tipoComprobanteDao?.guardar(TipoComprobante(nombreComprobante = "Factura"))
                            //Crear un empleado
                            usuarioDao?.guardar(Usuario(correo = "admin@admin.com", contrasena = "admin"))
                            empleadoDao?.guardar(Empleado(nombreEmpleado = "Admin", apellidoEmpleado = "Admin", telefonoEmpleado = "999999999",
                                dniEmpleado = "77777777",
                                idCargo = 1, idUsuario = 1))
                        }
                    }
                })
                    .build()
                    .also { INSTANCIA = it }
            }
        }
    }
}

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}