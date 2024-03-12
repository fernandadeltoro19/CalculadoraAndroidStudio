package com.example.micalculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.micalculadora.ui.theme.MiCalculadoraTheme
import android.view.View
import android.widget.Button
import android.widget.EditText


class MainActivity : ComponentActivity() {
    private lateinit var editText: EditText
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
    }

    fun onDigit(view: View) {
        editText.append((view as Button).text)
        lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            editText.append((view as Button).text)
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        val operator = (view as Button).text.toString()
        if (lastNumeric && !isOperatorAdded(editText.text.toString())) {
            editText.append(operator)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        editText.text.clear()
        lastNumeric = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = editText.text.toString()
            try {
                if (value.contains("/")) {
                    val splitValue = value.split("/")
                    val one = splitValue[0].toDouble()
                    val two = splitValue[1].toDouble()
                    value = (one / two).toString()
                } else if (value.contains("*")) {
                    val splitValue = value.split("\\*".toRegex())
                    val one = splitValue[0].toDouble()
                    val two = splitValue[1].toDouble()
                    value = (one * two).toString()
                } else if (value.contains("-")) {
                    val splitValue = value.split("-")
                    val one = splitValue[0].toDouble()
                    val two = splitValue[1].toDouble()
                    value = (one - two).toString()
                } else if (value.contains("+")) {
                    val splitValue = value.split("\\+".toRegex())
                    val one = splitValue[0].toDouble()
                    val two = splitValue[1].toDouble()
                    value = (one + two).toString()
                }
                editText.text.clear()
                editText.append(value)
                lastDot = if (value.contains(".")) {
                    false
                } else {
                    true
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("-") || value.contains("+")
        }
    }
}