package com.microcodes.encryptor

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.microcodes.encryptor.recyclerViews.DataListRecyclerView
import com.microcodes.encryptor.utils.Db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EncryptedDataListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypted_data_list)

        // Editing status bar
        supportActionBar?.title = "Encrypted Data List"
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
        val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!

        GlobalScope.launch(Dispatchers.Main) {
            val data = Db().getEncryptedData(deviceId)
            findViewById<RecyclerView>(R.id.dataListRecView).apply {
                adapter = DataListRecyclerView(this@EncryptedDataListActivity, data.toMutableList())
                layoutManager = LinearLayoutManager(this@EncryptedDataListActivity)

            }
        }


    }
}