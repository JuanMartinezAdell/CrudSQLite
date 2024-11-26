package com.example.clientes_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ClientesSQLite (context: Context): SQLiteOpenHelper(context,"Clientes.db", null, 1) {

    //Se ejecuta cuando arranca la aplicacion
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE usuarios(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                dni TEXT NOT NULL
            );
            """.trimIndent() //Ignoro los espacios con trimIndent y con la """ ignoro los retornos de carro

        db.execSQL(createTableQuery)
    }

    //Se ejecuta cuando la aplicacion actualice
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    // C R U D
    /**
     * Inserta un cliente en BBDD usando la clase Cliente
     * @param nuevoCliente los datos del nuevo cliente como objeto Cliente
     * @return el identificador del nuevo Cliente
     */

    fun insert(nuevoCliente: Cliente): Long {
        val db = getWritableDatabase() //traemos BDD en modo escritura
        val values = ContentValues()   //arrays asociativo clave valor
        values.put("nombre", nuevoCliente.nombre)
        values.put("dni", nuevoCliente.dni)
        val newId = db.insert("usuarios", null, values)
        db.close()
        return newId
    }

    fun read(idCliente: Long) : Cliente {

        val db = getReadableDatabase() //Obtengo BDD modo lectura
        val selectQuery ="SELECT * FROM usuarios WHERE id = "+idCliente
        val cursor: Cursor =  db.rawQuery(selectQuery,  arrayOf(idCliente.toString())) //asigno al cursor resultado de una consulta en BBDD
        var cliente = Cliente("","")
        if(cursor.moveToFirst()){
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
            cliente = Cliente(nombre,dni)
        }

        cursor.close()
        db.close()
        return cliente

    }

    fun update(idCliente: Long, nuevaInfoCliente: Cliente) : Int {
        val db = getWritableDatabase()
        val values = ContentValues()   //arrays asociativo clave valor
        values.put("nombre", nuevaInfoCliente.nombre)
        values.put("dni", nuevaInfoCliente.dni)

        val affectedRows = db.update("usuarios", values, "id = ?", arrayOf(idCliente.toString()))

        db.close()
        return affectedRows
    }

    fun delete(idCliente: Long) : Int {
        val db = getWritableDatabase()
        val affectedRows = db.delete("usuarios", "id = ?", arrayOf(idCliente.toString()))
        db.close()
        return affectedRows
    }

    /**
     * Numero de clientes BBDD
     */
    fun getNumeroClientes() : Int {
        val db = getReadableDatabase()
        val selectQuery = "SELECT count(*) as numClientes FROM usuarios"
        val cursor:Cursor = db.rawQuery(selectQuery, null)
        var num = 0

        if(cursor.moveToFirst()) {
            num = cursor.getInt(cursor.getColumnIndexOrThrow("numClientes"))
        }
        cursor.close()
        db.close()
        return num
    }


}