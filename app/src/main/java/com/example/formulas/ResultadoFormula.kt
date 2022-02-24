package com.example.formulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ResultadoFormula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_formula)

        val bundle = intent.extras
        val opcionFormula = bundle?.getInt("opcionFormula")

        when (opcionFormula) {
            1 -> {
                val solX1 = bundle?.getDouble("x1", 0.0)
                val solX2 = bundle?.getDouble("x2", 0.0)

                Toast.makeText(
                    this@ResultadoFormula,
                    "Parámetros obtenidos de la actividad anterior: solX1 = $solX1, solX2 = $solX2",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}