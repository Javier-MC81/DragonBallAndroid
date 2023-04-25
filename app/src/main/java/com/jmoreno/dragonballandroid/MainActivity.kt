package com.jmoreno.dragonballandroid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val nombreUsuario = "Javier"
    private val password = "230681"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFromPreferences()
    }
    private fun loadFromPreferences() {
        //val shared = getPreferences(Context.MODE_PRIVATE)
        //val numOld = shared.getInt("MiInteger", 0)

        //Revisar: let, apply, with, run
        getPreferences(Context.MODE_PRIVATE).apply {
            Log.w("tag", "${getString("MiNombre", "")}")
            Log.w("tag", "${getString("MiPassword", "")}")
        }

    }

    override fun onStop() {
        saveFromPreferences()
        super.onStop()

    }
    private fun saveFromPreferences() {
        // val shared = getPreferences(Context.MODE_PRIVATE)
        // val sharedPreferencesEditable = shared.edit()
        // sharedPreferencesEditable.putInt("MiInteger",numRandom)
        // sharedPreferencesEditable.apply()

        getPreferences(Context.MODE_PRIVATE).edit().apply {
            putString("MiNombre",nombreUsuario)
            putString("MiPassword", password)
            apply()
        }

    }

}