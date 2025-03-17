package com.example.proyecto01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class Practica3Activity : AppCompatActivity() {
    private lateinit var textViewContador: TextView
    private lateinit var textViewTiempo: TextView
    private lateinit var btnIncrementar: Button
    private lateinit var btnIniciar: Button
    private var contador = 0
    private var tiempoRestante = 10
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var temporizadorActivo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica3)

        // Inicializar vistas
        textViewContador = findViewById(R.id.textViewContador)
        textViewTiempo = findViewById(R.id.textViewTiempo)
        btnIncrementar = findViewById(R.id.btnIncrementar)
        btnIniciar = findViewById(R.id.btnIniciar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        // Deshabilitar botón incrementar hasta iniciar temporizador
        btnIncrementar.isEnabled = false

        // Definir el Runnable para el temporizador
        runnable = Runnable {
            tiempoRestante--
            if (tiempoRestante >= 0) {
                textViewTiempo.text = "Tiempo: $tiempoRestante segundos"
                handler.postDelayed(runnable, 1000)
            } else {
                finalizarTemporizador()
            }
        }

        // Configurar listeners
        btnIniciar.setOnClickListener {
            if (!temporizadorActivo) {
                iniciarTemporizador()
            }
        }

        btnIncrementar.setOnClickListener {
            contador++
            actualizarContador()
        }

        btnVolver.setOnClickListener {
            // Detener el temporizador si está activo
            if (temporizadorActivo) {
                handler.removeCallbacks(runnable)
            }
            finish()
        }
    }

    private fun iniciarTemporizador() {
        temporizadorActivo = true
        tiempoRestante = 10
        contador = 0
        actualizarContador()
        textViewTiempo.text = "Tiempo: $tiempoRestante segundos"

        // Habilitar botón de incrementar
        btnIncrementar.isEnabled = true

        // Deshabilitar botón de iniciar
        btnIniciar.isEnabled = false

        // Iniciar temporizador
        handler.postDelayed(runnable, 1000)
    }

    private fun finalizarTemporizador() {
        temporizadorActivo = false
        btnIncrementar.isEnabled = false
        btnIniciar.isEnabled = true

        // Mostrar un AlertDialog con el resultado
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Tiempo Finalizado!")
            .setMessage("Has hecho $contador clicks en 10 segundos")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun actualizarContador() {
        textViewContador.text = contador.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Asegurarse de detener el temporizador al destruir la actividad
        handler.removeCallbacks(runnable)
    }
}