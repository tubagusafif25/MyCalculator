package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Numbers
        val numberButtons = listOf(
            binding.buttonZero, binding.buttonOne, binding.buttonTwo,
            binding.buttonThree, binding.buttonFour, binding.buttonFive,
            binding.buttonSix, binding.buttonSeven, binding.buttonEight,
            binding.buttonNine, binding.buttonDot
        )

        numberButtons.forEach { btn ->
            btn.setOnClickListener {
                currentInput += (it as Button).text
                binding.solutionTv.text = currentInput
                updateResult()
            }
        }

        // 2. Setup Operators
        val operatorButtons = listOf(
            binding.buttonAddition, binding.buttonSubtraction,
            binding.buttonMultiplication, binding.buttonDivision
        )

        operatorButtons.forEach { btn ->
            btn.setOnClickListener {
                // Simple logic: add space around operators for easier parsing later
                currentInput += " ${(it as Button).text} "
                binding.solutionTv.text = currentInput
                updateResult()
            }
        }

        // 3. Setup Brackets
        binding.buttonOpenBracket.setOnClickListener {
            currentInput += "("
            binding.solutionTv.text = currentInput
            updateResult()
        }

        binding.buttonCloseBracket.setOnClickListener {
            currentInput += ")"
            binding.solutionTv.text = currentInput
            updateResult()
        }

        // 4. Clear and Backspace
        binding.buttonAc.setOnClickListener {
            currentInput = ""
            binding.solutionTv.text = "0"
            binding.resultTv.text = ""
        }

        binding.buttonBackspace.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.trim().dropLast(1).trim()
                binding.solutionTv.text = if (currentInput.isEmpty()) "0" else currentInput
                updateResult()
            }
        }

        // 5. Equals (Optional now, but can be used to "finish" calculation)
        binding.buttonEqual.setOnClickListener {
            if (binding.resultTv.text.isNotEmpty() && binding.resultTv.text != "Error") {
                // Remove the "=" sign and set currentInput to the result
                currentInput = binding.resultTv.text.toString().replace("= ", "")
                binding.solutionTv.text = currentInput
                binding.resultTv.text = ""
            }
        }
    }

    private fun updateResult() {
        if (currentInput.isEmpty()) {
            binding.resultTv.text = ""
            return
        }
        try {
            // Remove spaces and calculate
            val result = calculate(currentInput.replace(" ", ""))
            binding.resultTv.text = "= $result"
        } catch (e: Exception) {
            // If the expression is incomplete (e.g., "5+"), don't show error, just keep last result or empty
            // showing nothing or keeping it as it is is usually better for real-time
            binding.resultTv.text = ""
        }
    }

    // Matches the C++ function name and parameters
    external fun calculate(expression: String): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}