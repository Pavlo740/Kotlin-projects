package com.example.calculator_2

import BaseActivity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity



class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var screen: TextView
    private var currentInput: StringBuilder = StringBuilder()
    private var currentOperator: String? = null
    private var storedValue: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val num_0: Button = findViewById(R.id.num_0)
        val num_1: Button = findViewById(R.id.num_1)
        val num_2: Button = findViewById(R.id.num_2)
        val num_3: Button = findViewById(R.id.num_3)
        val num_4: Button = findViewById(R.id.num_4)
        val num_5: Button = findViewById(R.id.num_5)
        val num_6: Button = findViewById(R.id.num_6)
        val num_7: Button = findViewById(R.id.num_7)
        val num_8: Button = findViewById(R.id.num_8)
        val num_9: Button = findViewById(R.id.num_9)

        val num_c: Button = findViewById(R.id.num_c)
        val num_ac: Button = findViewById(R.id.num_ac)
        val num_krapka: Button = findViewById(R.id.num_krapka)
        val num_plus_and_munys: Button = findViewById(R.id.num_plus_and_munys)
        val num_vidsotky: Button = findViewById(R.id.num_vidsotky)
        val num_dilenya: Button = findViewById(R.id.num_dilenya)
        val num_mno: Button = findViewById(R.id.num_mno)
        val num_plus: Button = findViewById(R.id.num_plus)
        val num_munus: Button = findViewById(R.id.num_munus)
        val num_doriv: Button = findViewById(R.id.num_doriv)

        screen = findViewById(R.id.screen)


        val buttons = arrayOf(
            num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9,
            num_c, num_ac, num_krapka, num_plus_and_munys, num_vidsotky, num_dilenya,
            num_mno, num_plus, num_munus, num_doriv
        )

        for (button in buttons) {
            button.setOnClickListener(this)
        }
        screen = findViewById(R.id.screen)
    }


    override fun onClick(v: View?) {
        if (v is Button) {
            when (v.id) {
                R.id.num_c -> {
                    clearLastInput()
                }

                R.id.num_ac -> {
                    clearAll()
                }

                R.id.num_plus_and_munys -> {
                    changeSign()
                }

                R.id.num_vidsotky -> {
                    calculatePercentage()
                }

                R.id.num_krapka -> {
                    addDot()
                }

                R.id.num_plus, R.id.num_munus, R.id.num_mno, R.id.num_dilenya -> {
                    handleOperator(v.text.toString())
                }

                R.id.num_doriv -> {
                    performCalculation()
                }

                else -> {
                    val buttonText = v.text?.toString() ?: ""
                    handleInput(buttonText)
                }
            }
        }
    }

    private fun handleOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            storedValue = currentInput.toString().toDouble()
            currentInput.clear()
            currentOperator = operator
        }
    }

    private fun performCalculation() {
        if (currentInput.isNotEmpty() && currentOperator != null) {
            val inputValue = currentInput.toString().toDouble()
            when (currentOperator) {
                "+" -> currentInput.clear().append(storedValue + inputValue)
                "-" -> currentInput.clear().append(storedValue - inputValue)
                "*" -> currentInput.clear().append(storedValue * inputValue)
                "/" -> {
                    if (inputValue != 0.0) {
                        currentInput.clear().append(storedValue / inputValue)
                    } else {
                        // Handle division by zero, for example, by displaying an error message
                        currentInput.clear()
                        screen.text = "Помилка"
                        return
                    }
                }
            }
            screen.text = currentInput.toString()
            currentOperator = null
        }
    }


    private fun handleInput(input: String) {

        if (currentInput.length + input.length <= 8) {


            currentInput.append(input)
        }


        if (currentInput.length > 8) {
            currentInput.delete(8, currentInput.length)
        }

        screen.text = currentInput.toString()
    }

    private fun clearLastInput() {

        if (currentInput.isNotEmpty()) {
            currentInput.deleteCharAt(currentInput.length - 1)
            screen.text = currentInput.toString()
        }
    }

    private fun clearAll() {

        currentInput.clear()
        screen.text = ""
    }

    private fun changeSign() {

        if (currentInput.isNotEmpty() && currentInput.toString().toDouble() != 0.0) {
            val currentValue = currentInput.toString().toDouble()
            currentInput.clear()
            currentInput.append((-currentValue).toString())
            screen.text = currentInput.toString()
        }
    }

    private fun calculatePercentage() {

        if (currentInput.isNotEmpty() && currentInput.toString().toDouble() != 0.0) {
            val currentValue = currentInput.toString().toDouble()
            val percentageValue = currentValue / 100
            currentInput.clear()
            currentInput.append(percentageValue.toString())
            screen.text = currentInput.toString()
        }
    }

    private fun addDot() {

        if (currentInput.isNotEmpty() && !currentInput.contains(".")) {
            currentInput.append(".")
            screen.text = currentInput.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Збереження стану додатку перед перезавантаженням
        outState.putString("currentInput", currentInput.toString())
        outState.putString("currentOperator", currentOperator)
        outState.putDouble("storedValue", storedValue)

        // Викликайте батьківський метод, щоб зберегти стан
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Відновлення збереженого стану після перезавантаження
        super.onRestoreInstanceState(savedInstanceState)

        // Отримання даних з Bundle
        val savedInput = savedInstanceState.getString("currentInput")
        val savedOperator = savedInstanceState.getString("currentOperator")
        val savedStoredValue = savedInstanceState.getDouble("storedValue")

        // Відновлення даних в змінні вашого додатку
        currentInput.clear()
        currentInput.append(savedInput)
        currentOperator = savedOperator
        storedValue = savedStoredValue

        // Оновлення елементів інтерфейсу за потреби
        screen.text = currentInput.toString()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_converter -> {
                val inputValue = screen.text.toString()
                val intent = Intent(this, ConverterActivity::class.java)
                intent.putExtra("startValue", inputValue)
                startActivity(intent)
                return true
            }
            R.id.action_exit -> {
                showExitDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}







