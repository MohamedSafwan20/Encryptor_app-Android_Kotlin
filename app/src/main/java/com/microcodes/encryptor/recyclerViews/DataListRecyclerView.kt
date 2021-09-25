package com.microcodes.encryptor.recyclerViews

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.microcodes.encryptor.R
import com.microcodes.encryptor.utils.Db

class DataListRecyclerView(context: Context, private val items: MutableList<Map<String, String>>) :
    RecyclerView.Adapter<DataListRecyclerView.DataListViewHolder>() {
    private val sharedpreferences: SharedPreferences =
        context.getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
    private val deviceId = sharedpreferences.getString("deviceId", "No device Id")!!

    inner class DataListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recview_data_list_item, parent, false)
        return DataListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataListViewHolder, position: Int) {
        holder.itemView.apply {
            val message = findViewById<TextView>(R.id.message)
            val hashTextField = findViewById<EditText>(R.id.hashTextField)

            message.text = items[position]["message"]
            hashTextField.setText(items[position]["hash"])

            findViewById<Button>(R.id.showHashBtn).setOnClickListener {
                hashTextField.inputType =
                    EditorInfo.TYPE_TEXT_VARIATION_NORMAL
            }

            findViewById<Button>(R.id.deleteItemBtn).setOnClickListener {
                AlertDialog.Builder(context).setTitle("Are you Sure?")
                    .setMessage("The hash ${items[position]["hash"]} wil be removed permanently.")
                    .setPositiveButton("Yes") { _, _ ->
                        val res = Db().deleteDataItem(
                            deviceId,
                            hashTextField.text.toString()
                        )

                        if (res) {
                            items.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create().show()

            }

            findViewById<Button>(R.id.copyHashBtn).setOnClickListener {
                val clipBoard: ClipboardManager =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipBoard.setPrimaryClip(
                    ClipData.newPlainText(
                        "hash",
                        findViewById<EditText>(R.id.hashTextField).text
                    )
                )

                Toast.makeText(context, "Hash copied to clipboard", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}