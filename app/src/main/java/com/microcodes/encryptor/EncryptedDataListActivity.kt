package com.microcodes.encryptor

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class EncryptedDataListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypted_data_list)

        // Editing status bar
        supportActionBar?.title = "Encrypted Data List"
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

//        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
//        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!


    }
}