package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.myapplication2.R

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput: String = "0"
    private var previousInput: Double = 0.0
    private var currentOperator: String = ""
    private var operatorClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Set click listeners for all buttons
        val buttons = arrayOf(
            findViewById<Button>(R.id.button_0),
            findViewById<Button>(R.id.button_1),
            findViewById<Button>(R.id.button_2),
            findViewById<Button>(R.id.button_3),
            findViewById<Button>(R.id.button_4),
            findViewById<Button>(R.id.button_5),
            findViewById<Button>(R.id.button_6),
            findViewById<Button>(R.id.button_7),
            findViewById<Button>(R.id.button_8),
            findViewById<Button>(R.id.button_9),
            findViewById<Button>(R.id.button_plus),
            findViewById<Button>(R.id.button_minus),
            findViewById<Button>(R.id.button_multiply),
            findViewById<Button>(R.id.button_divide),
            findViewById<Button>(R.id.button_dot),
            findViewById<Button>(R.id.button_equals),
            findViewById<Button>(R.id.button_ac),
            findViewById<Button>(R.id.button_plus_minus),
            findViewById<Button>(R.id.button_percent)
        )

        for (button in buttons) {
            button.setOnClickListener { onButtonClick(button) }
        }
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9 -> {
                onDigitButtonClick(button.text.toString())
            }

            R.id.button_plus, R.id.button_minus, R.id.button_multiply, R.id.button_divide -> {
                onOperatorButtonClick(button.text.toString())
            }

            R.id.button_dot -> onDotButtonClick()
            R.id.button_equals -> onEqualsButtonClick()
            R.id.button_ac -> onClearButtonClick()
            R.id.button_plus_minus -> onPlusMinusButtonClick()
            R.id.button_percent -> onPercentButtonClick()
        }
    }

    private fun onDigitButtonClick(digit: String) {
        if (operatorClicked) {
            currentInput = digit
            operatorClicked = false
        } else {
            if (currentInput == "0" && digit != "0") {
                currentInput = digit // Replace the initial 0 with the digit
            } else {
                currentInput += digit
            }
        }
        updateDisplay()
    }

    private fun onOperatorButtonClick(operator: String) {
        if (!operatorClicked) {
            previousInput = currentInput.toDouble()
        }
        currentOperator = operator
        operatorClicked = true
    }


    private fun onEqualsButtonClick() {
        val currentNumber = currentInput.toDouble()
        currentInput = when (currentOperator) {
            "+" -> (previousInput + currentNumber).toString()
            "-" -> (previousInput - currentNumber).toString()
            "*" -> (previousInput * currentNumber).toString()
            "/" -> (previousInput / currentNumber).toString()
            else -> currentInput
        }
        operatorClicked = false
        updateDisplay()
    }

    private fun onClearButtonClick() {
        currentInput = "0"
        previousInput = 0.0
        currentOperator = ""
        operatorClicked = false
        updateDisplay()
    }

    private fun onDotButtonClick() {
        if (!currentInput.contains(".")) {
            currentInput += "."
        }
        updateDisplay()
    }

    private fun onPlusMinusButtonClick() {
        currentInput = if (currentInput.startsWith("-")) {
            currentInput.substring(1)
        } else {
            "-$currentInput"
        }
        updateDisplay()
    }

    private fun onPercentButtonClick() {
        currentInput = (currentInput.toDouble() / 100).toString()
        updateDisplay()
    }

    private fun updateDisplay() {
        // If the number is an integer, show it without decimal places
        display.text = if (currentInput.toDoubleOrNull()?.rem(1) == 0.0) {
            currentInput.toDouble().toInt().toString()
        } else {
            currentInput
        }
    }
}
