package com.example.proyecto01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar y configurar los botones
        setupButtons()
    }

    private fun setupButtons() {
        val btnPractica1 = findViewById<Button>(R.id.btnPractica1)
        val btnPractica2 = findViewById<Button>(R.id.btnPractica2)
        val btnPractica3 = findViewById<Button>(R.id.btnPractica3)
        val btnPractica4 = findViewById<Button>(R.id.btnPractica4)
        val btnPractica5 = findViewById<Button>(R.id.btnPractica5)
        val btnPractica6 = findViewById<Button>(R.id.btnPractica6)

        // Configurar los listeners para cada botón
        btnPractica1.setOnClickListener {
            startActivity(Intent(this, Practica1Activity::class.java))
        }

        btnPractica2.setOnClickListener {
            startActivity(Intent(this, Practica2Activity::class.java))
        }

        btnPractica3.setOnClickListener {
            startActivity(Intent(this, Practica3Activity::class.java))
        }

        btnPractica4.setOnClickListener {
            startActivity(Intent(this, Practica4Activity::class.java))
        }

        btnPractica5.setOnClickListener {
            startActivity(Intent(this, Practica5Activity::class.java))
        }

        btnPractica6.setOnClickListener {
            startActivity(Intent(this, Practica6Activity::class.java))
        }
    }
}