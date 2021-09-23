package com.microcodes.encryptor

import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.microcodes.encryptor.recyclerViews.DataListRecyclerView
import com.microcodes.encryptor.utils.Db

class EncryptedDataListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypted_data_list)

        // Editing status bar
        supportActionBar?.title = "Encrypted Data List"
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

//        findViewById<ProgressBar>(R.id.loadingIndicator).visibility = View.VISIBLE

        val data = Db().getEncryptedData("000")
        findViewById<RecyclerView>(R.id.dataListRecView).apply {
            adapter = DataListRecyclerView(data)
            layoutManager = LinearLayoutManager(this@EncryptedDataListActivity)
        }

//        findViewById<ProgressBar>(R.id.loadingIndicator).visibility = View.GONE




//        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
//        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!


    }
}