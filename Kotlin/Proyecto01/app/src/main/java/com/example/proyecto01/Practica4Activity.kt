package com.example.proyecto01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Practica4Activity : AppCompatActivity() {
    private lateinit var textViewContador: TextView
    private lateinit var textViewTiempo: TextView
    private lateinit var textViewHistorial: TextView
    private lateinit var btnIncrementar: Button
    private lateinit var btnIniciar: Button
    private var contador = 0
    private var tiempoRestante = 10
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var temporizadorActivo = false
    private val FILENAME = "historial_scores.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica4)

        // Inicializar vistas
        textViewContador = findViewById(R.id.textViewContador)
        textViewTiempo = findViewById(R.id.textViewTiempo)
        textViewHistorial = findViewById(R.id.textViewHistorial)
        btnIncrementar = findViewById(R.id.btnIncrementar)
        btnIniciar = findViewById(R.id.btnIniciar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        // Deshabilitar botón incrementar hasta iniciar temporizador
        btnIncrementar.isEnabled = false

        // Cargar historial existente
        cargarHistorial()

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

        // Guardar score en archivo
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
            val contenido = "$fecha - Score: $contador clicks\n"

            val fos = openFileOutput(FILENAME, MODE_APPEND)
            fos.write(contenido.toByteArray())
            fos.close()

            // Recargar historial
            cargarHistorial()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun cargarHistorial() {
        try {
            val fis = openFileInput(FILENAME)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder()
            var linea: String?

            while (br.readLine().also { linea = it } != null) {
                sb.append(linea).append('\n')
            }

            textViewHistorial.text = sb.toString()
            br.close()
        } catch (e: Exception) {
            textViewHistorial.text = "No hay registros guardados"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Asegurarse de detener el temporizador al destruir la actividad
        handler.removeCallbacks(runnable)
    }
}