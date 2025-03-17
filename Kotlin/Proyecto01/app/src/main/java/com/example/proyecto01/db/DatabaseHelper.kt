package com.example.proyecto01.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "proyecto01.db"

        // Tabla Usuarios (para Práctica 5)
        const val TABLE_USERS = "usuarios"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_NOMBRE = "nombre"
        const val COLUMN_USER_EDAD = "edad"

        // Tabla Scores (para Práctica 6)
        const val TABLE_SCORES = "scores"
        const val COLUMN_SCORE_ID = "id"
        const val COLUMN_SCORE_VALOR = "valor"
        const val COLUMN_SCORE_FECHA = "fecha"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de usuarios
        val createTableUsers = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USER_NOMBRE TEXT,"
                + "$COLUMN_USER_EDAD INTEGER)")
        db.execSQL(createTableUsers)

        // Crear tabla de scores
        val createTableScores = ("CREATE TABLE $TABLE_SCORES ("
                + "$COLUMN_SCORE_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_SCORE_VALOR INTEGER,"
                + "$COLUMN_SCORE_FECHA TEXT)")
        db.execSQL(createTableScores)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Si hay una actualización, eliminar tablas existentes y crear nuevas
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCORES")
        onCreate(db)
    }

    // Métodos para manejo de usuarios (Práctica 5)
    fun addUser(usuario: UserModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NOMBRE, usuario.nombre)
        values.put(COLUMN_USER_EDAD, usuario.edad)

        // Insertar fila
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun getAllUsers(): ArrayList<UserModel> {
        val userList = ArrayList<UserModel>()
        val selectQuery = "SELECT * FROM $TABLE_USERS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NOMBRE))
                val edad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_EDAD))

                val user = UserModel(id, nombre, edad)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    // Métodos para manejo de scores (Práctica 6)
    fun addScore(score: ScoreModel): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_SCORE_VALOR, score.valor)
        values.put(COLUMN_SCORE_FECHA, score.fecha)

        // Insertar fila
        val id = db.insert(TABLE_SCORES, null, values)
        db.close()
        return id
    }

    fun getAllScores(): ArrayList<ScoreModel> {
        val scoreList = ArrayList<ScoreModel>()
        val selectQuery = "SELECT * FROM $TABLE_SCORES ORDER BY $COLUMN_SCORE_ID DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE_ID))
                val valor = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE_VALOR))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCORE_FECHA))

                val score = ScoreModel(id, valor, fecha)
                scoreList.add(score)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return scoreList
    }
}