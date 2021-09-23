package com.microcodes.encryptor

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.microcodes.encryptor.utils.Db
import com.microcodes.encryptor.utils.Helpers

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Editing status bar
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!

        findViewById<Button>(R.id.registerBtn).setOnClickListener {
            val pinInput = findViewById<EditText>(R.id.newPinInput).text.toString()
            val confirmPinInput = findViewById<EditText>(R.id.newConfirmPinInput).text.toString()
            val errorText = findViewById<TextView>(R.id.errorTextRegister)

            val isValid = isValidUser(pinInput, confirmPinInput)
            if (isValid == 0) {
                val userRegistered = registerUser(deviceId, pinInput)

                if (userRegistered) Helpers.redirectTo(
                    this,
                    MainActivity()
                )
                else errorText.setText(R.string.register_error)

            } else {
                errorText.text = resources.getString(isValid)
            }
        }
    }

    private fun isValidUser(pin: String, confirmPin: String): Int {
        return if (pin.isNotBlank() && confirmPin.isNotBlank()) {
            if (pin == confirmPin) {
                0
            } else R.string.pin_mismatch_error
        } else R.string.pin_blank_error
    }

    private fun registerUser(deviceId: String, pin: String): Boolean {
        val userId = Db().registerUser(deviceId, pin)
        return userId.isNotEmpty()
    }
}