package com.microcodes.encryptor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DecryptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decrypt)

        // Editing status bar
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

//        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
//        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!
    }
}