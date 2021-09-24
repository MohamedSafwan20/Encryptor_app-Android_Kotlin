package com.microcodes.encryptor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.microcodes.encryptor.utils.Db

class DecryptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decrypt)

        // Editing status bar
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!

        findViewById<Button>(R.id.decryptBtn).setOnClickListener {
            val hashcode = findViewById<EditText>(R.id.hashcodeText).text.toString()
            val errorText = findViewById<TextView>(R.id.errorTextDecrypt)

            val isValid = isValidData(hashcode)
            if (isValid == 0) {
                val res = Db().getEncryption(hashcode, deviceId)

                if (res.isNotEmpty()) {
                    findViewById<EditText>(R.id.dataText).setText(res["value"])
                } else Toast.makeText(this, "No encryption found.", Toast.LENGTH_LONG).show()

            } else {
                errorText.text = resources.getString(isValid)
            }
        }

        findViewById<Button>(R.id.copyDataBtn).setOnClickListener {
            val clipBoard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipBoard.setPrimaryClip(
                ClipData.newPlainText(
                    "data",
                    findViewById<EditText>(R.id.dataText).text
                )
            )

            Toast.makeText(this, "Data copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

    private fun isValidData(hashcode: String): Int {
        return if (hashcode.isNotBlank()) {
            0
        } else R.string.decrypt_hash_error
    }
}