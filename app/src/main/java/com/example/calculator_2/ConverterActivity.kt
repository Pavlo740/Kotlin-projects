package com.example.calculator_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView


class ConverterActivity : AppCompatActivity() {

    private lateinit var inputTextView: EditText
    private lateinit var outputTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        inputTextView = findViewById(R.id.inputTextView)
        outputTextView = findViewById(R.id.outputTextView)

        val startValue = intent.getStringExtra("startValue")

        // Встановлюємо значення у inputTextView
        val inputTextView: EditText = findViewById(R.id.inputTextView)
        inputTextView.setText(startValue.toString())



        // Запускаємо функцію для налаштування Spinner
        setupSpinner()
    }
    private fun setupSpinner() {
        val spinner: Spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.conversion_types,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "Довжина" -> {
                        setInputUnitSpinner(R.array.length_units)
                        setOutputUnitSpinner(R.array.length_units) // Для налаштування другого спіннера
                    }
                    "Маса" -> {
                        setInputUnitSpinner(R.array.mass_units)
                        setOutputUnitSpinner(R.array.mass_units) // Для налаштування другого спіннера
                        convertUnit() // Викликаємо функцію конвертації маси
                    }
                    "Температура" -> {
                        setInputUnitSpinner(R.array.temperature_units)
                        setOutputUnitSpinner(R.array.temperature_units) // Для налаштування другого спіннера
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setInputUnitSpinner(arrayResId: Int) {
        val value1Spinner: Spinner = findViewById(R.id.value1)
        val adapter = ArrayAdapter.createFromResource(
            this,
            arrayResId,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        value1Spinner.adapter = adapter

        value1Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertUnit() // Викликаємо функцію конвертації після вибору одиниць маси
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setOutputUnitSpinner(arrayResId: Int) {
        val value2Spinner: Spinner = findViewById(R.id.value2)
        val adapter = ArrayAdapter.createFromResource(
            this,
            arrayResId,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        value2Spinner.adapter = adapter

        value2Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertUnit() // Викликаємо функцію конвертації після вибору одиниць маси
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun convertUnit() {
        val inputValue = inputTextView.text.toString().toDoubleOrNull()
        val inputUnit = (findViewById<Spinner>(R.id.value1)).selectedItem.toString()
        val outputUnit = (findViewById<Spinner>(R.id.value2)).selectedItem.toString()

        if (inputValue != null) {
            when (inputUnit) {
                "мм" -> convertFromMm(inputValue, outputUnit)
                "см" -> convertFromCm(inputValue, outputUnit)
                "м" -> convertFromMeter(inputValue, outputUnit)
                "км" -> convertFromKm(inputValue, outputUnit)
                "г" -> convertFromGram(inputValue, outputUnit)
                "кг" -> convertFromKilogram(inputValue, outputUnit)
                "фунт" -> convertFromPound(inputValue, outputUnit)
                "тонна" -> convertFromTon(inputValue, outputUnit)
                "°C" -> convertFromCelsius(inputValue, outputUnit)
                "°F" -> convertFromFahrenheit(inputValue, outputUnit)
                "K" -> convertFromKelvin(inputValue, outputUnit)
            }
        } else {
            // Додайте повідомлення про помилку, якщо введене значення неправильне
            outputTextView.text = "Невірне значення"
        }
    }

    private fun convertFromMm(inputValue: Double, targetUnit: String) {
        val result: Double = when (targetUnit) {
            "мм" -> inputValue // якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            "см" -> inputValue / 10
            "м" -> inputValue / 1000
            "км" -> inputValue / 1000000
            else -> inputValue // якщо одиниця виміру не визначена, повернути те саме значення
        }
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromCm(inputValue: Double, targetUnit: String) {
        val result: Double = when (targetUnit) {
            "мм" -> inputValue * 10 // конвертувати сантиметри у міліметри
            "м" -> inputValue / 100 // конвертувати сантиметри у метри
            "км" -> inputValue / 100000 // конвертувати сантиметри у кілометри
            else -> inputValue // якщо одиниця виміру співпадає з вихідною, повернути те саме значення
        }
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromMeter(inputValue: Double, targetUnit: String) {
        val result: Double = when (targetUnit) {
            "мм" -> inputValue * 1000 // конвертувати метри у міліметри
            "см" -> inputValue * 100 // конвертувати метри у сантиметри
            "км" -> inputValue / 1000 // конвертувати метри у кілометри
            else -> inputValue // якщо одиниця виміру співпадає з вихідною, повернути те саме значення
        }
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromKm(inputValue: Double, targetUnit: String) {
        val result: Double = when (targetUnit) {
            "мм" -> inputValue * 1000000 // конвертувати кілометри у міліметри
            "см" -> inputValue * 100000 // конвертувати кілометри у сантиметри
            "м" -> inputValue * 1000 // конвертувати кілометри у метри
            else -> inputValue // якщо одиниця виміру співпадає з вихідною, повернути те саме значення
        }
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }



    private fun convertFromGram(inputValue: Double, targetUnit: String) {
        // Конвертація з грамів в вказану одиницю маси
        val result: Double = when (targetUnit) {
            "г" -> inputValue // Якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            "кг" -> inputValue / 1000.0 // Конвертувати грами у кілограми
            "фунт" -> inputValue * 0.00220462 // Конвертувати грами у фунти
            "тонна" -> inputValue * 0.000001 // Конвертувати грами у тонни
            else -> inputValue // Якщо одиниця виміру не визначена, повернути те саме значення
        }
        // Форматування результату і виведення його у вікно виводу
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromKilogram(inputValue: Double, targetUnit: String) {
        // Конвертація з кілограмів в вказану одиницю маси
        val result: Double = when (targetUnit) {
            "г" -> inputValue * 1000 // Конвертувати кілограми у грами
            "кг" -> inputValue // Якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            "фунт" -> inputValue * 2.20462 // Конвертувати кілограми у фунти
            "тонна" -> inputValue * 0.001 // Конвертувати кілограми у тонни
            else -> inputValue // Якщо одиниця виміру не визначена, повернути те саме значення
        }
        // Форматування результату і виведення його у вікно виводу
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromPound(inputValue: Double, targetUnit: String) {
        // Конвертація з фунтів в вказану одиницю маси
        val result: Double = when (targetUnit) {
            "г" -> inputValue * 453.592 // Конвертувати фунти у грами
            "кг" -> inputValue * 0.453592 // Конвертувати фунти у кілограми
            "фунт" -> inputValue // Якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            "тонна" -> inputValue * 0.000453592 // Конвертувати фунти у тонни
            else -> inputValue // Якщо одиниця виміру не визначена, повернути те саме значення
        }
        // Форматування результату і виведення його у вікно виводу
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromTon(inputValue: Double, targetUnit: String) {
        // Конвертація з тонн в вказану одиницю маси
        val result: Double = when (targetUnit) {
            "г" -> inputValue * 1000000 // Конвертувати тонни у грами
            "кг" -> inputValue * 1000 // Конвертувати тонни у кілограми
            "фунт" -> inputValue * 2204.62 // Конвертувати тонни у фунти
            "тонна" -> inputValue // Якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            else -> inputValue // Якщо одиниця виміру не визначена, повернути те саме значення
        }
        // Форматування результату і виведення його у вікно виводу
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }


    private fun convertFromCelsius(inputValue: Double, targetUnit: String) {
        val result: Double = when (targetUnit) {
            "°C" -> inputValue // Якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            "°F" -> inputValue * 9 / 5 + 32 // Конвертувати градуси Цельсія у Фаренгейти
            "K" -> inputValue + 273.15 // Конвертувати градуси Цельсія у Кельвіни
            else -> inputValue // Якщо одиниця виміру не визначена, повернути те саме значення
        }
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromFahrenheit(inputValue: Double, targetUnit: String) {
        val result: Double = when (targetUnit) {
            "°C" -> (inputValue - 32) * 5 / 9 // Конвертувати градуси Фаренгейта у Цельсії
            "°F" -> inputValue // Якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            "K" -> (inputValue + 459.67) * 5 / 9 // Конвертувати градуси Фаренгейта у Кельвіни
            else -> inputValue // Якщо одиниця виміру не визначена, повернути те саме значення
        }
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }

    private fun convertFromKelvin(inputValue: Double, targetUnit: String) {
        val result: Double = when (targetUnit) {
            "°C" -> inputValue - 273.15 // Конвертувати Кельвіни у градуси Цельсія
            "°F" -> inputValue * 9 / 5 - 459.67 // Конвертувати Кельвіни у градуси Фаренгейта
            "K" -> inputValue // Якщо одиниця виміру співпадає з вихідною, повернути те саме значення
            else -> inputValue // Якщо одиниця виміру не визначена, повернути те саме значення
        }
        val resultString = String.format("%.6f", result).trimEnd('0').trimEnd('.')
        outputTextView.text = resultString
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.converter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_open_calculator -> {
                // Відкриття MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}