package com.example.proyecto01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto01.db.DatabaseHelper
import com.example.proyecto01.db.ScoreModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Practica6Activity : AppCompatActivity() {
    private lateinit var textViewContador: TextView
    private lateinit var textViewTiempo: TextView
    private lateinit var btnIncrementar: Button
    private lateinit var btnIniciar: Button
    private lateinit var recyclerViewScores: RecyclerView
    private lateinit var scoreAdapter: ScoreAdapter
    private lateinit var dbHelper: DatabaseHelper

    private var contador = 0
    private var tiempoRestante = 10
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var temporizadorActivo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica6)

        // Inicializar vistas
        textViewContador = findViewById(R.id.textViewContador)
        textViewTiempo = findViewById(R.id.textViewTiempo)
        btnIncrementar = findViewById(R.id.btnIncrementar)
        btnIniciar = findViewById(R.id.btnIniciar)
        recyclerViewScores = findViewById(R.id.recyclerViewScores)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        // Inicializar base de datos
        dbHelper = DatabaseHelper(this)

        // Configurar RecyclerView
        recyclerViewScores.layoutManager = LinearLayoutManager(this)
        scoreAdapter = ScoreAdapter(ArrayList())
        recyclerViewScores.adapter = scoreAdapter

        // Cargar scores existentes
        cargarScores()

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

        // Guardar score en SQLite
        guardarScore()

        // Mostrar un AlertDialog con el resultado
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Tiempo Finalizado!")
            .setMessage("Has hecho $contador clicks en 10 segundos\nTu score ha sido guardado.")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun actualizarContador() {
        textViewContador.text = contador.toString()
    }

    private fun guardarScore() {
        try {
            val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            val scoreModel = ScoreModel(valor = contador, fecha = fecha)

            // Guardar en base de datos
            dbHelper.addScore(scoreModel)

            // Recargar scores
            cargarScores()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun cargarScores() {
        val scoreList = dbHelper.getAllScores()
        scoreAdapter.updateData(scoreList)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Asegurarse de detener el temporizador al destruir la actividad
        handler.removeCallbacks(runnable)
    }
}