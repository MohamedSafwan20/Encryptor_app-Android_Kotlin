package com.microcodes.encryptor.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

class Helpers {
    companion object {
        fun redirectTo(
            context: Context,
            activity: Activity,
            data: Map<String, String> = emptyMap()
        ) {
            Intent(context, activity::class.java).also {
                if (data.isNotEmpty())
                    it.putExtra(data["key"], data["value"])
                context.startActivity(it)
            }
        }
    }
}