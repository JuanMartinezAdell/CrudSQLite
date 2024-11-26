package com.example.clientes_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val c1 = Cliente("Mariano Rodriguez", "2233444S")
        val c2 = Cliente("Irene Macias", "44544555L")
        val c3 = Cliente("Jose Ramirez", "4568899Q")

        val bbbClientes = ClientesSQLite(this)
        bbbClientes.insert(c1)
        bbbClientes.insert(c2)
        bbbClientes.insert(c3)
    }


}