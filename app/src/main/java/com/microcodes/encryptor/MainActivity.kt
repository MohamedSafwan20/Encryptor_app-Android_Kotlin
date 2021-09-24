package com.microcodes.encryptor

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.microcodes.encryptor.utils.Db
import com.microcodes.encryptor.utils.Helpers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Editing status bar
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!

        findViewById<Button>(R.id.encryptBtn).setOnClickListener {
            val message = findViewById<EditText>(R.id.message).text.toString()
            val data = findViewById<EditText>(R.id.data).text.toString()
            val errorText = findViewById<TextView>(R.id.errorTextMain)

            val isValid = isValidData(message, data)
            if (isValid == 0) {
                val res = Db().saveEncryption(message, data, deviceId)

                if (res) {
                    findViewById<EditText>(R.id.message).setText("")
                    findViewById<EditText>(R.id.data).setText("")
                    Toast.makeText(this, "Data Encryption Successful", Toast.LENGTH_LONG).show()
                    Helpers.redirectTo(this, EncryptedDataListActivity())
                } else Toast.makeText(this, "Error making encryption", Toast.LENGTH_LONG).show()

            } else {
                errorText.text = resources.getString(isValid)
            }
        }

        findViewById<Button>(R.id.encryptListActivityBtn).setOnClickListener {
            Helpers.redirectTo(this, EncryptedDataListActivity())
        }

    }

    private fun isValidData(message: String, data: String): Int {
        return if (message.isNotBlank() && data.isNotBlank()) {
            0
        } else R.string.main_blank_error
    }
}