package com.example.formulas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.formulas.databinding.ActivityInicioFormulasBinding
import kotlin.math.pow
import kotlin.math.sqrt

class InicioFormulas : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val DEBUG_TAG = "InicioFormulas_TAG"

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
        val camposValidos = validaCampoNoVacio()
        parametros.putInt("opcionFormula", opcionFormula)
        intent = Intent(this, ResultadoFormula::class.java)
        with(binding) {
            when (opcionFormula) {
                Constantes.OPCION_FORMULA_CUADRATICA -> {
                    if (!camposValidos) return
                    var paramA = getIntEditText(etFormulaCuadA)
                    var paramB = getIntEditText(etFormulaCuadB)
                    var paramC = getIntEditText(etFormulaCuadC)
                    var ecuacion = formatEcuacion(paramA, paramB, paramC)
                    val raices: Array<Double> = raicesEcuacionSegundoGrado(paramA, paramB, paramC)
                    if (raices.isEmpty()) {
                        Toast.makeText(
                            this@InicioFormulas,
                            "Lo sentimos, no hay soluciones reales para la ecuacion " +
                                    "$ecuacion, intente ingresar otros valores",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }
                    parametros.putString("ecuacion", ecuacion)
                    parametros.putDouble("x1", raices[0])
                    parametros.putDouble("x2", raices[1])
                }
                Constantes.OPCION_AREA_TRAPECIO -> {
                    if (!camposValidos) return
                    var paramBMayor = getIntEditText(etAreaTrapecioBMayor)
                    var paramBMenor = getIntEditText(etAreaTrapecioBMenor)
                    var paramH = getIntEditText(etAreaTrapecioH)
                    val areaTrapecio = areaTrapecio(paramBMayor, paramBMenor, paramH)
                    parametros.putInt("paramBMayor", paramBMayor)
                    parametros.putInt("paramBMenor", paramBMenor)
                    parametros.putInt("paramH", paramH)
                    parametros.putInt("areaTrapecio", areaTrapecio)
                }
                Constantes.OPCION_DISTANCIA_DOS_PUNTOS -> {
                    if (!camposValidos) return
                    var x1 = getIntEditText(etDistX1)
                    var y1 = getIntEditText(etDistY1)
                    var x2 = getIntEditText(etDistX2)
                    var y2 = getIntEditText(etDistY2)
                    val distanciaPuntos = distanciaDosPuntos(x1, y1, x2, y2)
                    parametros.putInt("x1", x1)
                    parametros.putInt("y1", y1)
                    parametros.putInt("x2", x2)
                    parametros.putInt("y2", y2)
                    parametros.putDouble("distanciaPuntos", distanciaPuntos)
                }
                else -> return
            }
        }
        intent.putExtras(parametros)
        startActivity(intent)
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

    // Validaciones
    private fun validaCampoNoVacio(): Boolean {
        var valido = true
        with(binding) {
            when (opcionFormula) {
                Constantes.OPCION_FORMULA_CUADRATICA -> {
                    if (campoVacio(etFormulaCuadA)) valido = false
                    if (campoVacio(etFormulaCuadB)) valido = false
                    if (campoVacio(etFormulaCuadC)) valido = false
                }
                Constantes.OPCION_AREA_TRAPECIO -> {
                    if (campoVacio(etAreaTrapecioBMayor)) valido = false
                    if (campoVacio(etAreaTrapecioBMenor)) valido = false
                    if (campoVacio(etAreaTrapecioH)) valido = false
                }
                Constantes.OPCION_DISTANCIA_DOS_PUNTOS -> {
                    if (campoVacio(etDistX1)) valido = false
                    if (campoVacio(etDistY1)) valido = false
                    if (campoVacio(etDistX2)) valido = false
                    if (campoVacio(etDistY2)) valido = false
                }
            }
        }
        return valido
    }

    // Auxiliares

    private fun campoVacio(editText: EditText): Boolean {
        var esVacio = false
        if (editText.text.toString() == "") {
            editText.error = "El campo no puede estar vacío"
            esVacio = true
        }
        return esVacio
    }

    private fun getIntEditText(editText: EditText): Int {
        return editText.text.toString().toInt()
    }

    private fun clearEditTexts() {
        with(binding) {
            etFormulaCuadA.text.clear()
            etFormulaCuadB.text.clear()
            etFormulaCuadC.text.clear()
            etAreaTrapecioBMayor.text.clear()
            etAreaTrapecioBMenor.text.clear()
            etAreaTrapecioH.text.clear()
            etDistX1.text.clear()
            etDistX2.text.clear()
            etDistY1.text.clear()
            etDistY2.text.clear()
        }
    }

    private fun formatEcuacion(a: Int, b: Int, c: Int): String {
        return "${if (a == 1) "" else a}x^2 + ${if (b == 1) "" else "($b)"} + ${if (c < 0) "($c)" else c}"
    }


    // Layout functions
    private fun aplicaConstraint(idView: Int) {
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.btnResolver,
            ConstraintSet.TOP,
            idView,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(constraintLayout)
    }

    private fun showFormulaCuadratica() {
        clearEditTexts()
        with(binding) {
            // Escondemos área del trapecio
            linearLayoutAreaTrapecioBMayor.visibility = View.INVISIBLE
            linearLayoutAreaTrapecioBMenor.visibility = View.INVISIBLE
            linearLayoutAreaTrapecioH.visibility = View.INVISIBLE

            // Escondemos distancia entre dos puntos
            linearLayoutDistX1Y1.visibility = View.INVISIBLE
            linearLayoutDistX2Y2.visibility = View.INVISIBLE

            // Mostramos formula cuadrática
            linearLayoutFormulaCuadA.visibility = View.VISIBLE
            linearLayoutFormulaCuadB.visibility = View.VISIBLE
            linearLayoutFormulaCuadC.visibility = View.VISIBLE
            ivFormulas.setImageResource(R.drawable.ecuacion_cuadratica)
            aplicaConstraint(R.id.linearLayoutFormulaCuadC)
        }
    }

    private fun showAreaTrapecio() {
        clearEditTexts()
        with(binding) {
            // Escondemos formula cuadrática
            linearLayoutFormulaCuadA.visibility = View.INVISIBLE
            linearLayoutFormulaCuadB.visibility = View.INVISIBLE
            linearLayoutFormulaCuadC.visibility = View.INVISIBLE

            // Escondemos distancia entre dos puntos
            linearLayoutDistX1Y1.visibility = View.INVISIBLE
            linearLayoutDistX2Y2.visibility = View.INVISIBLE

            // Mostramos area del trapecio
            linearLayoutAreaTrapecioBMayor.visibility = View.VISIBLE
            linearLayoutAreaTrapecioBMenor.visibility = View.VISIBLE
            linearLayoutAreaTrapecioH.visibility = View.VISIBLE
            ivFormulas.setImageResource(R.drawable.area_trapecio)
            aplicaConstraint(R.id.linearLayoutAreaTrapecioH)
        }
    }

    private fun showDistanciaDosPuntos() {
        clearEditTexts()
        with(binding) {
            // Escondemos área del trapecio
            linearLayoutAreaTrapecioBMayor.visibility = View.INVISIBLE
            linearLayoutAreaTrapecioBMenor.visibility = View.INVISIBLE
            linearLayoutAreaTrapecioH.visibility = View.INVISIBLE

            // Escondemos formula cuadrática
            linearLayoutFormulaCuadA.visibility = View.INVISIBLE
            linearLayoutFormulaCuadB.visibility = View.INVISIBLE
            linearLayoutFormulaCuadC.visibility = View.INVISIBLE

            // Mostramos distancia entre dos puntos
            linearLayoutDistX1Y1.visibility = View.VISIBLE
            linearLayoutDistX2Y2.visibility = View.VISIBLE
            ivFormulas.setImageResource(R.drawable.distancia_dos_puntos)
            aplicaConstraint(R.id.linearLayoutDistX2Y2)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        opcionFormula = pos
        constraintSet = ConstraintSet()
        Log.d(DEBUG_TAG, "opcionFormula: $opcionFormula")
        with(binding) {
            constraintLayout = clInicioFormulas
            when (opcionFormula) {
                Constantes.OPCION_FORMULA_CUADRATICA -> showFormulaCuadratica()
                Constantes.OPCION_AREA_TRAPECIO -> showAreaTrapecio()
                Constantes.OPCION_DISTANCIA_DOS_PUNTOS -> showDistanciaDosPuntos()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}