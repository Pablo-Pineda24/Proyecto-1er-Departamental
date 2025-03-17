package com.example.proyecto01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto01.db.DatabaseHelper
import com.example.proyecto01.db.UserModel

class Practica5Activity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var btnGuardar: Button
    private lateinit var recyclerViewUsuarios: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica5)

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextEdad = findViewById(R.id.editTextEdad)
        btnGuardar = findViewById(R.id.btnGuardar)
        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        // Inicializar base de datos
        dbHelper = DatabaseHelper(this)

        // Configurar RecyclerView
        recyclerViewUsuarios.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(ArrayList())
        recyclerViewUsuarios.adapter = userAdapter

        // Cargar usuarios existentes
        cargarUsuarios()

        // Configurar listeners
        btnGuardar.setOnClickListener {
            guardarUsuario()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun guardarUsuario() {
        val nombre = editTextNombre.text.toString().trim()
        val edadStr = editTextEdad.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show()
            return
        }

        if (edadStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese una edad", Toast.LENGTH_SHORT).show()
            return
        }

        val edad = edadStr.toInt()

        // Crear modelo de usuario
        val usuario = UserModel(nombre = nombre, edad = edad)

        // Guardar en base de datos
        val id = dbHelper.addUser(usuario)

        if (id > 0) {
            Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show()

            // Limpiar campos
            editTextNombre.text.clear()
            editTextEdad.text.clear()

            // Actualizar lista
            cargarUsuarios()
        } else {
            Toast.makeText(this, "Error al guardar usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarUsuarios() {
        val userList = dbHelper.getAllUsers()
        userAdapter.updateData(userList)
    }
}