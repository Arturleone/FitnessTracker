package com.example.fitnesstracker

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IMCActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imcactivity)

        editWeight = findViewById(R.id.editTextText3)
        editHeight = findViewById(R.id.editTextText5)

        val x = 10
        if (x == 10) {

        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if(!validate()) {
                Toast.makeText(this, "Todos os campos devem ser obrigatórios", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                val weight = editWeight.text.toString().toInt()
                val height = editHeight.text.toString().toInt()
                val result = calculateIMC(weight, height)

                val imcResponseId = imcResponse(result)

                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.imc_response, result))
                    .setMessage(imcResponseId)
                    .setPositiveButton(android.R.string.ok) { dialog, which ->

                    }
                    .create()
                    .show()

                val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                
            }
        }
    }

    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && !editHeight.text.toString().startsWith("0")
                && !editWeight.text.toString().startsWith("0"))
    }

    private fun calculateIMC(weight: Int, height: Int): Double {
        return weight / ((height / 100.0) * (height / 100.0))
    }

    @StringRes
    private fun imcResponse (imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_soverely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_law_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extrene_weight
        }
    }
}