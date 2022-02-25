package com.example.formulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.formulas.databinding.ActivityInicioFormulasBinding
import com.example.formulas.databinding.ActivityResultadoFormulaBinding

class ResultadoFormula : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoFormulaBinding
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoFormulaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.extras!!
        val opcionFormula = bundle?.getInt("opcionFormula")
        muestraResultado(opcionFormula)
    }

    private fun muestraResultado(opcionFormula: Int?) {
        var textoResultado = ""
        when (opcionFormula) {
            Constantes.OPCION_FORMULA_CUADRATICA -> {
                val ecuacion = bundle?.getString("ecuacion", "")
                val solX1 = bundle?.getDouble("x1", 0.0)
                val solX2 = bundle?.getDouble("x2", 0.0)

                textoResultado =
                    "Las soluciones de la ecuación $ecuacion" +
                            " son: x1 = ${String.format("%.3f", solX1)}" +
                            " y x2 = ${String.format("%.3f", solX2)}"
            }
            Constantes.OPCION_AREA_TRAPECIO -> {
                val paramBMayor = bundle?.getInt("paramBMayor", 0)
                val paramBMenor = bundle?.getInt("paramBMenor", 0)
                val paramH = bundle?.getInt("paramH", 0)
                val areaTrapecio = bundle?.getInt("areaTrapecio", 0)

                textoResultado =
                    "El área del trapecio con base mayor $paramBMayor, base menor $paramBMenor" +
                            " y altura $paramH es $areaTrapecio"
            }
            Constantes.OPCION_DISTANCIA_DOS_PUNTOS -> {
                val x1 = bundle?.getInt("x1", 0)
                val y1 = bundle?.getInt("y1", 0)
                val x2 = bundle?.getInt("x2", 0)
                val y2 = bundle?.getInt("y2", 0)
                val distanciaPuntos = bundle?.getDouble("distanciaPuntos", 0.0)

                textoResultado =
                    "La distancia entre los puntos ($x1, $y1) y ($x2, $y2)" +
                            " es ${String.format("%.3f", distanciaPuntos)}"
            }
        }
        binding.tvMuestraResultado.text = textoResultado
    }
}