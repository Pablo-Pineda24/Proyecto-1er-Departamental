package com.example.proyecto01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Practica2Activity : AppCompatActivity() {
    private lateinit var textViewContador: TextView
    private lateinit var btnIncrementar: Button
    private lateinit var btnReiniciar: Button
    private var contador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica2)

        // Inicializar vistas
        textViewContador = findViewById(R.id.textViewContador)
        btnIncrementar = findViewById(R.id.btnIncrementar)
        btnReiniciar = findViewById(R.id.btnReiniciar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        // Configurar listeners
        btnIncrementar.setOnClickListener {
            contador++
            actualizarContador()
        }

        btnReiniciar.setOnClickListener {
            contador = 0
            actualizarContador()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun actualizarContador() {
        textViewContador.text = contador.toString()
    }
}