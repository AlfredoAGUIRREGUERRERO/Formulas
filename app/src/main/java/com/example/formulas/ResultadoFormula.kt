package com.example.formulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formulas.databinding.ActivityResultadoFormulaBinding

class ResultadoFormula : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoFormulaBinding
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoFormulaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.extras!!
        val opcionFormula = bundle.getInt("opcionFormula")
        muestraResultado(opcionFormula)
    }

    private fun muestraResultado(opcionFormula: Int?) {
        var textoResultado = ""
        when (opcionFormula) {
            Constantes.OPCION_FORMULA_CUADRATICA -> {
                val ecuacion = bundle.getString("ecuacion", "")
                val solX1 = bundle.getDouble("x1", 0.0)
                val solX2 = bundle.getDouble("x2", 0.0)

                textoResultado = getString(R.string.equation_sols, ecuacion, solX1, solX2)
            }
            Constantes.OPCION_AREA_TRAPECIO -> {
                val paramBMayor = bundle.getInt("paramBMayor", 0)
                val paramBMenor = bundle.getInt("paramBMenor", 0)
                val paramH = bundle.getInt("paramH", 0)
                val areaTrapecio = bundle.getInt("areaTrapecio", 0)

                textoResultado =
                    getString(
                        R.string.area_trapecio,
                        paramBMayor,
                        paramBMenor,
                        paramH,
                        areaTrapecio
                    )
            }
            Constantes.OPCION_DISTANCIA_DOS_PUNTOS -> {
                val x1 = bundle.getInt("x1", 0)
                val y1 = bundle.getInt("y1", 0)
                val x2 = bundle.getInt("x2", 0)
                val y2 = bundle.getInt("y2", 0)
                val distanciaPuntos = bundle.getDouble("distanciaPuntos", 0.0)

                textoResultado =
                    getString(R.string.distancia_puntos, x1, y1, x2, y2, distanciaPuntos)
            }
        }
        binding.tvMuestraResultado.text = textoResultado
    }
}