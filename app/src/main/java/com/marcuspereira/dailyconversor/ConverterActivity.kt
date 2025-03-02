package com.marcuspereira.dailyconversor

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.marcuspereira.dailyconversor.databinding.ActivityConverterBinding

class ConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rbWeight.isChecked = true

        val allViews = listOf(
            binding.spinnerWeightInput,
            binding.spinnerWeightOutput,
            binding.tvWeightInput,
            binding.tvWeightOutput,
            binding.spinnerLengthInput,
            binding.spinnerLengthOutput,
            binding.tvLengthInput,
            binding.tvLengthOutput,
            binding.spinnerVolumeInput,
            binding.spinnerVolumeOutput,
            binding.tvVolumeInput,
            binding.tvVolumeOutput,
            binding.spinnerTemperatureInput,
            binding.spinnerTemperatureOutput,
            binding.tvTemperatureInput,
            binding.tvTemperatureOutput
        )

        var unitInputWeight = ""
        var unitOutputWeight = ""

        var unitInputLength = ""
        var unitOutputLength = ""

        var unitInputVolume = ""
        var unitOutputVolume = ""

        var unitInputTemperature = ""
        var unitOutputTemperature = ""

        arrayAdapter(binding.spinnerWeightInput, R.array.spinner_weight)
        binding.spinnerWeightInput.setSelection(0)
        arrayAdapter(binding.spinnerWeightOutput, R.array.spinner_weight)
        binding.spinnerWeightOutput.setSelection(4)

        arrayAdapter(binding.spinnerLengthInput, R.array.spinner_length)
        binding.spinnerLengthInput.setSelection(0)
        arrayAdapter(binding.spinnerLengthOutput, R.array.spinner_length)
        binding.spinnerLengthOutput.setSelection(2)

        arrayAdapter(binding.spinnerVolumeInput, R.array.spinner_volume)
        binding.spinnerVolumeInput.setSelection(0)
        arrayAdapter(binding.spinnerVolumeOutput, R.array.spinner_volume)
        binding.spinnerVolumeOutput.setSelection(1)

        arrayAdapter(binding.spinnerTemperatureInput, R.array.spinner_temperature)
        binding.spinnerTemperatureInput.setSelection(1)
        arrayAdapter(binding.spinnerTemperatureOutput, R.array.spinner_temperature)
        binding.spinnerTemperatureOutput.setSelection(2)

        optionSelectedSpinner(binding.spinnerWeightInput) { unitInputWeight = it }
        optionSelectedSpinner(binding.spinnerWeightOutput) { unitOutputWeight = it }

        optionSelectedSpinner(binding.spinnerLengthInput) { unitInputLength = it }
        optionSelectedSpinner(binding.spinnerLengthOutput) { unitOutputLength = it }

        optionSelectedSpinner(binding.spinnerVolumeInput) { unitInputVolume = it }
        optionSelectedSpinner(binding.spinnerVolumeOutput) { unitOutputVolume = it }

        optionSelectedSpinner(binding.spinnerTemperatureInput) { unitInputTemperature = it }
        optionSelectedSpinner(binding.spinnerTemperatureOutput) { unitOutputTemperature = it }


        binding.btnCalculate.setOnClickListener {

            if (binding.tieWeight.text!!.isEmpty() && binding.tieLength.text!!.isEmpty()
                && binding.tieVolume.text!!.isEmpty() && binding.tieTemperature.text!!.isEmpty())
            {
                Snackbar.make(
                    this,
                    binding.tieWeight,
                    "Insira um valor válido para que a conversão possa ser feita",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                val result = when {
                    binding.rbWeight.isChecked -> convertWeight(
                        binding.tieWeight.text.toString().toDouble(),
                        unitInputWeight,
                        unitOutputWeight
                    ) . let{it to formatResult(unitOutputWeight)}

                    binding.rbLength.isChecked -> convertLength(
                        binding.tieLength.text.toString().toDouble(),
                        unitInputLength,
                        unitOutputLength
                    ) . let{it to formatResult(unitOutputLength)}

                    binding.rbVolume.isChecked -> convertVolume(
                        binding.tieVolume.text.toString().toDouble(),
                        unitInputVolume,
                        unitOutputVolume
                    ) . let{it to formatResult(unitOutputVolume)}

                    binding.rbTemperature.isChecked -> convertTemperature(
                        binding.tieTemperature.text.toString().toDouble(),
                        unitInputTemperature,
                        unitOutputTemperature
                    ) . let{it to formatResult(unitOutputTemperature)}

                    else -> 0.0 to ""
                }

                binding.tvResultConverter.text = "${result.first} ${result.second}"

            }

        }

        binding.btnRefresh.setOnClickListener {
            clean()
        }

        binding.spinnerWeightInput.visibility = View.VISIBLE
        binding.spinnerWeightOutput.visibility = View.VISIBLE
        binding.tieWeight.visibility = View.VISIBLE

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            allViews.forEach { it.visibility = View.GONE}

            binding.tieWeight.isEnabled = false
            binding.tieLength.isEnabled = false
            binding.tieVolume.isEnabled = false
            binding.tieTemperature.isEnabled = false

            binding.tvWeightInput.visibility = View.GONE
            binding.tvLengthInput.visibility = View.GONE
            binding.tvVolumeInput.visibility = View.GONE
            binding.tvTemperatureInput.visibility = View.GONE
            binding.tvWeightOutput.visibility = View.GONE
            binding.tvLengthOutput.visibility = View.GONE
            binding.tvVolumeOutput.visibility = View.GONE
            binding.tvTemperatureOutput.visibility = View.GONE

            when (checkedId) {
                R.id.rbWeight -> {
                    binding.spinnerWeightInput.visibility = View.VISIBLE
                    binding.spinnerWeightOutput.visibility = View.VISIBLE
                    binding.tieWeight.visibility = View.VISIBLE
                    binding.tieWeight.isEnabled = true
                    binding.tvWeightInput.visibility = View.VISIBLE
                    binding.tvWeightOutput.visibility = View.VISIBLE
                }

                R.id.rbLength -> {
                    binding.spinnerLengthInput.visibility = View.VISIBLE
                    binding.spinnerLengthOutput.visibility = View.VISIBLE
                    binding.tieLength.visibility = View.VISIBLE
                    binding.tieLength.isEnabled = true
                    binding.tvLengthInput.visibility = View.VISIBLE
                    binding.tvLengthOutput.visibility = View.VISIBLE
                }

                R.id.rbVolume -> {
                    binding.spinnerVolumeInput.visibility = View.VISIBLE
                    binding.spinnerVolumeOutput.visibility = View.VISIBLE
                    binding.tieVolume.visibility = View.VISIBLE
                    binding.tieVolume.isEnabled = true
                    binding.tvVolumeInput.visibility = View.VISIBLE
                    binding.tvVolumeOutput.visibility = View.VISIBLE
                }

                R.id.rbTemperature -> {
                    binding.spinnerTemperatureInput.visibility = View.VISIBLE
                    binding.spinnerTemperatureOutput.visibility = View.VISIBLE
                    binding.tieTemperature.visibility = View.VISIBLE
                    binding.tieTemperature.isEnabled = true
                    binding.tvTemperatureInput.visibility = View.VISIBLE
                    binding.tvTemperatureOutput.visibility = View.VISIBLE
                }
            }

            clean()
        }

        binding.radioGroup.clearCheck()
        binding.radioGroup.check(R.id.rbWeight)

    }

    private fun formatResult(measureSelected: String): String{

        val output = when (measureSelected){
            "Miligrama (mg)" -> "mg"
            "Grama (g)" -> "g"
            "Onça (oz)" -> "oz"
            "Libra (lb)" -> "lb"
            "Quilograma (kg)" -> "kg"
            "Stone (st)" -> "st"
            "Tonelada (t)" -> "t"
            "Milímetro (mm)" -> "mm"
            "Centímetro (cm)" -> "cm"
            "Quilômetro (km)" -> "km"
            "Polegada (in)" -> "in"
            "Pé (ft)" -> "ft"
            "Jarda (yd)" -> "yd"
            "Milha (mi)" -> "mi"
            "Metro (m)" -> "m"
            "Mililitro (mL)" -> "mL"
            "Litro (L)" -> "L"
            "Galão americano (gal US)" -> "gal US"
            "Galão imperial (gal UK)" -> "gal UK"
            "Metro cúbico (m³)" -> "m³"
            "Kelvin (K)" -> "K"
            "Celsius (°C)" -> "°C"
            "Fahrenheit (°F)" -> "°F"
            else -> ""
        }

        return output

    }

    private fun clean() {
        binding.tieLength.setText("")
        binding.tieWeight.setText("")
        binding.tieVolume.setText("")
        binding.tieTemperature.setText("")
        binding.tvResultConverter.text = "0.0"
    }

    private fun convertWeight(value: Double, input: String, output: String): Double {
        val kgValue = when (input) {
            "Miligrama (mg)" -> value / 1_000_000
            "Grama (g)" -> value / 100
            "Onça (oz)" -> value * 0.0283495
            "Libra (lb)" -> value * 0.453592
            "Quilograma (kg)" -> value
            "Stone (st)" -> value * 6.35029
            "Tonelada (t)" -> value * 1000
            else -> 0.0
        }

        val result =  when (output) {
            "Miligrama (mg)" -> kgValue * 1_000_000
            "Grama (g)" -> kgValue * 1000
            "Onça (oz)" -> kgValue / 0.0283495
            "Libra (lb)" -> kgValue / 0.453592
            "Quilograma (kg)" -> kgValue
            "Stone (st)" -> kgValue / 6.35029
            "Tonelada (t)" -> kgValue / 1000
            else -> 0.0
        }


        val resultFormat = String.format("%.2f", result)
        return resultFormat.toDouble()
    }

    private fun convertLength(value: Double, input: String, output: String): Double {

        val meterValue = when (input) {
            "Milímetro (mm)" -> value / 1000
            "Centímetro (cm)" -> value / 100
            "Quilômetro (km)" -> value * 1000
            "Polegada (in)" -> value * 0.0254
            "Pé (ft)" -> value * 0.3048
            "Jarda (yd)" -> value * 0.9144
            "Milha (mi)" -> value * 1609.34
            "Metro (m)" -> value
            else -> 0.0
        }

        val result =  when (output) {
            "Milímetro (mm)" -> meterValue * 1000
            "Centímetro (cm)" -> meterValue * 100
            "Quilômetro (km)" -> meterValue / 1000
            "Polegada (in)" -> meterValue / 0.0254
            "Pé (ft)" -> meterValue / 0.3048
            "Jarda (yd)" -> meterValue / 0.9144
            "Milha (mi)" -> meterValue / 1609.34
            "Metro (m)" -> meterValue
            else -> 0.0
        }

        val resultFormat = String.format("%.2f", result)
        return resultFormat.toDouble()
    }

    private fun convertVolume(value: Double, input: String, output: String): Double {
        val cubicMeterValue = when (input) {
            "Mililitro (mL)" -> value / 1_000_000
            "Litro (L)" -> value / 1000
            "Galão americano (gal US)" -> value * 0.00378541
            "Galão imperial (gal UK)" -> value * 0.00454609
            "Metro cúbico (m³)" -> value
            else -> 0.0
        }

        val result = when (output) {
            "Mililitro (mL)" -> cubicMeterValue * 1_000_000
            "Litro (L)" -> cubicMeterValue * 1000
            "Galão americano (gal US)" -> cubicMeterValue / 0.00378541
            "Galão imperial (gal UK)" -> cubicMeterValue / 0.00454609
            "Metro cúbico (m³)" -> cubicMeterValue
            else -> 0.0
        }
        val resultFormat = String.format("%.2f", result)
        return resultFormat.toDouble()
    }

    private fun convertTemperature(value: Double, input: String, output: String): Double {
        val celsiusValue = when (input) {
            "Kelvin (K)" -> value - 273.15
            "Celsius (°C)" -> value
            "Fahrenheit (°F)" -> (value - 32) * 5 / 9
            else -> 0.0
        }

        val result = when (output) {
            "Kelvin (K)" -> celsiusValue + 273.15
            "Celsius (°C)" -> celsiusValue
            "Fahrenheit (°F)" -> (celsiusValue * 9 / 5) + 32
            else -> 0.0
        }
        val resultFormat = String.format("%.3f", result)
        return resultFormat.toDouble()
    }

    private fun arrayAdapter(spinner: Spinner, arrayResId: Int) {
        val adapter = ArrayAdapter.createFromResource(
            spinner.context, arrayResId, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun optionSelectedSpinner(spinner: Spinner, onItemSelected: (String) -> Unit) {

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedUnit = parent?.getItemAtPosition(position).toString()
                onItemSelected(selectedUnit)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}

