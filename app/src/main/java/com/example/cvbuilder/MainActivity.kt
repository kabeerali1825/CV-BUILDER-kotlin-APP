package com.example.cvbuilder

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var cvRecyclerView: RecyclerView
    private lateinit var saveButton: Button
    private lateinit var cvAdapter: CVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        dbHelper = DBHelper(this)
        cvRecyclerView = binding.cvRecyclerView
        saveButton = binding.saveButton

        // Initialize RecyclerView and adapter
        cvAdapter = CVAdapter(this, dbHelper.getAllCVs())
        cvRecyclerView.layoutManager = LinearLayoutManager(this)
        cvRecyclerView.adapter = cvAdapter

        // Initialize the degree spinner
        val degreeSpinner = binding.degreeSpinner
        val degreeOptions = arrayOf("Bachelors", "Masters", "PHD")
        val degreeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, degreeOptions)
        degreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        degreeSpinner.adapter = degreeAdapter

        saveButton.setOnClickListener {
            // Get data from the UI using View Binding
            val rollNumber = binding.rollNumberEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val cgpa = binding.cgpaEditText.text.toString().toDouble()
            val degree = degreeSpinner.selectedItem.toString()
            val genderId = binding.genderRadioGroup.checkedRadioButtonId
            val gender = findViewById<RadioButton>(genderId).text.toString()

            val interests = mutableListOf<String>()
            if (binding.academiaCheckBox.isChecked) interests.add("Academia")
            if (binding.industryCheckBox.isChecked) interests.add("Industry")
            if (binding.businessCheckBox.isChecked) interests.add("Business")
            val interestsStr = interests.joinToString(", ")

            // Insert the CV record into the database
            val result = dbHelper.insertCV(rollNumber, name, cgpa, degree, gender, "", interestsStr)

            if (result > 0) {
                // Display a success message
                showToast("CV data saved to the database")
            } else {
                // Display an error message
                showToast("Failed to save CV data")
            }

            // Refresh the CV list view
            cvAdapter.updateCVs(dbHelper.getAllCVs())
        }

        // Display CV records
        cvAdapter.updateCVs(dbHelper.getAllCVs())
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
