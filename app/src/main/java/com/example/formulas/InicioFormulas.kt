package com.example.formulas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.graphics.drawable.toDrawable
import com.example.formulas.databinding.ActivityInicioFormulasBinding
import kotlin.math.pow
import kotlin.math.sqrt

class InicioFormulas : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val DEBUG_TAG = "InicioFormulas_TAG"
    private val OPCION_FORMULA_CUADRATICA = 1

    private var opcionFormula = 0
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var constraintSet: ConstraintSet
    private lateinit var binding: ActivityInicioFormulasBinding
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioFormulasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner definition
        spinner = binding.spFormulas
        ArrayAdapter.createFromResource(
            this,
            R.array.formulas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

    fun calcularFormula(view: View) {
        val parametros = Bundle()
        parametros.putInt("opcionFormula", opcionFormula)
        intent = Intent(this, ResultadoFormula::class.java)
        with(binding) {
            when (opcionFormula) {
                OPCION_FORMULA_CUADRATICA -> {
                    TODO("Revisar si no hay nada en los edittext")
                    var paramA: Int = etFormulaCuadA.text.toString().toInt()
                    var paramB: Int = etFormulaCuadB.text.toString().toInt()
                    var paramC: Int = etFormulaCuadC.text.toString().toInt()
                    val raices: Array<Double> = raicesEcuacionSegundoGrado(paramA, paramB, paramC)
                    if (raices.isEmpty()) return TODO("En la otra actividad dirá que no hay soluciones reales?")
                    /*Log.d(
                        DEBUG_TAG,
                        "Las raíces de ${if (paramA == 1) "" else paramA}x^2 + " +
                                "${if (paramB == 1) "" else paramB}x + " +
                                "$paramC son x1 = ${raices[0]} y x2 ${raices[1]}"
                    )*/
                    parametros.putDouble("x1", raices[0])
                    parametros.putDouble("x2", raices[1])
                    intent.putExtras(parametros)
                    startActivity(intent)
                }
                else -> return
            }
        }
    }

    // Fórmulas a resolver

    /**
     * Da las raices REALES de una ecuación de segundo grado
     */
    private fun raicesEcuacionSegundoGrado(a: Int, b: Int, c: Int): Array<Double> {
        val discriminante = b.toDouble().pow(2) - 4 * a * c
        Log.d(DEBUG_TAG, "Discriminante = $discriminante")
        if (discriminante < 0) return emptyArray()
        val x1 = (-b + discriminante) / (2 * a)
        val x2 = (-b - discriminante) / (2 * a)
        return arrayOf(x1, x2)
    }

    /**
     * Calcula el área de un trapecio
     * B: base mayor
     * b: base menor
     * h: algura
     * ((B + b) / 2) * h
     */

    private fun areaTrapecio(B: Int, b: Int, h: Int): Int {
        return (B + b) / 2 * h
    }

    /**
     * Calcula la distancia entre dos puntos en el plano cartesiano
     * Distancia entre (x1, y1), (x2, y2) es
     * sqrt((x2 - x1)^2 + (y2 - y1)^2)
     */
    private fun distanciaDosPuntos(x1: Int, y1: Int, x2: Int, y2: Int): Double {
        val difPowX = (x2 - x1).toDouble().pow(2)
        val difPowY = (y2 - y1).toDouble().pow(2)
        return sqrt(difPowX + difPowY)
    }

    private fun showFormulaCuadratica() {
        with(binding) {
            linearLayoutFormulaCuadA.visibility = View.VISIBLE
            linearLayoutFormulaCuadB.visibility = View.VISIBLE
            linearLayoutFormulaCuadC.visibility = View.VISIBLE
            ivFormulas.setImageResource(R.drawable.ecuacion_cuadratica)
            constraintSet.clone(constraintLayout)
            constraintSet.connect(
                R.id.btnResolver,
                ConstraintSet.TOP,
                R.id.linearLayoutFormulaCuadC,
                ConstraintSet.BOTTOM
            )
            constraintSet.applyTo(constraintLayout)
        }
    }

    private fun showwAreaTrapecio() {
        TODO("Implementar vistas")
    }

    private fun showDistanciaDosPuntos() {
        TODO("Implementar vistas")
    }

    private fun hideEverything() {
        with(binding) {
            // Escondemos formula cuadrática
            linearLayoutFormulaCuadA.visibility = View.INVISIBLE
            linearLayoutFormulaCuadB.visibility = View.INVISIBLE
            linearLayoutFormulaCuadC.visibility = View.INVISIBLE

            ivFormulas.setImageResource(android.R.color.transparent)
            constraintSet.clone(constraintLayout)
            constraintSet.connect(
                R.id.btnResolver,
                ConstraintSet.TOP,
                R.id.spFormulas,
                ConstraintSet.BOTTOM
            )
            constraintSet.applyTo(constraintLayout)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        opcionFormula = pos
        constraintSet = ConstraintSet()
        Log.d(DEBUG_TAG, "opcionFormula: $opcionFormula")
        with(binding) {
            constraintLayout = clInicioFormulas
            when (opcionFormula) {
                OPCION_FORMULA_CUADRATICA -> showFormulaCuadratica()
                else -> hideEverything()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}