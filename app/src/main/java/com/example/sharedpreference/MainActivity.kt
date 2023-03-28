package com.example.sharedpreference

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Secure.putString
import android.view.LayoutInflater
import com.example.sharedpreference.databinding.ActivityMainBinding

/**
 * Shared Preferences is the way in which one can store and retrieve small amounts of primitive
 * data as key/value pairs to a file on the device storage such as String, int, float,
 * Boolean that make up your preferences in an XML file inside the app on the device storage.
 *
 * Shared Preferences are suitable for different situations. For example, when the user’s settings
 * need to be saved or to store data that can be used in different activities within the app.
 * As you know, onPause() will always be called before your activity is placed in the background
 * or destroyed, So for the data to be saved persistently, it’s preferred to save it in onPause(),
 * which could be restored in onCreate() of the activity. The data stored using shared preferences
 * are kept private within the scope of the application.
 */
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize binding
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSave.setOnClickListener { saveData() }
        binding.btnLoad.setOnClickListener { loadData() }

    }

    private fun saveData() {
        val name = binding.etName.text.toString()

        /**
         * handling "NumberFormatException" here using elvis operator
         * toIntOrNull(): it will return null if the input is string
         * and if it is null then will return the default value of 0
         */
        val age = binding.etAge.text.toString().toIntOrNull() ?: 0

        val isAdult = binding.checkBox.isChecked

        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("NAME", name)
            putInt("AGE", age)
            putBoolean("IS_ADULT", isAdult)
            apply()
        }
    }

    private fun loadData() {
        val sharedPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedName = sharedPref.getString("NAME", null)
        val savedAge = sharedPref.getInt("AGE", 0)
        val checkedBox = sharedPref.getBoolean("IS_ADULT", false)

        //set the values to view
        binding.etName.setText(savedName)
        binding.etAge.setText(savedAge.toString())
        binding.checkBox.isChecked = checkedBox
    }
}