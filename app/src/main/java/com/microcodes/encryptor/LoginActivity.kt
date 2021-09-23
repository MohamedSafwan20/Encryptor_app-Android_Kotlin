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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Editing status bar
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            val pinInput = findViewById<EditText>(R.id.pinInput).text.toString()
            val errorText = findViewById<TextView>(R.id.errorTextLogin)

            val isValid = isValidUser(pinInput)
            if (isValid == 0) {
                val userExists = loginUser(deviceId, pinInput)

                if (userExists) Helpers.redirectTo(
                    this,
                    MainActivity()
                )
                else errorText.setText(R.string.login_error)

            } else {
                errorText.text = resources.getString(isValid)
            }
        }
    }

    private fun isValidUser(pin: String): Int {
        return if (pin.isNotBlank()) {
            0
        } else R.string.pin_blank_error
    }

    private fun loginUser(deviceId: String, pin: String): Boolean {
        return Db().loginUser(deviceId, pin)
    }
}