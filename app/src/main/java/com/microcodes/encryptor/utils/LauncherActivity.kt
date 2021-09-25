package com.microcodes.encryptor.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.microcodes.encryptor.LoginActivity
import com.microcodes.encryptor.R
import com.microcodes.encryptor.RegisterActivity

class LauncherActivity : AppCompatActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        val sharedpreferences = getSharedPreferences("Encryptor_Preferences", Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString("deviceId", deviceId)
        editor.apply()

        if (isUserHaveAccount(deviceId)) {
            Helpers.redirectTo(
                this,
                LoginActivity()
            )
        } else
            Helpers.redirectTo(
                this,
                RegisterActivity()
            )
    }

    private fun isUserHaveAccount(deviceId: String): Boolean {
        val userData = Db().checkUserExists(deviceId)
        return userData.documents.size > 0
    }
}