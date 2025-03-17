package com.example.proyecto01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Practica1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica1)

        // Configurar botón para volver al menú principal
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }
    }
}