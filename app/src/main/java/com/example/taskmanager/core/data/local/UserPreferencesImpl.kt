package com.example.taskmanager.core.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.taskmanager.core.domain.local.UserPreferences
import com.example.taskmanager.core.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    @ApplicationContext context: Context
): UserPreferences {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val pref = EncryptedSharedPreferences.create(
        context,
        "pref",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveUser(user: User) {
        pref.edit {
            putString(UserPreferences.TOKEN, user.token)
            putString(UserPreferences.USERNAME, user.username)
            putString(UserPreferences.EMAIL, user.email)
        }
    }

    override fun getUser(): User? {
        val token = pref.getString(UserPreferences.TOKEN, null)
        val username = pref.getString(UserPreferences.USERNAME, null)
        val email = pref.getString(UserPreferences.EMAIL, null)

        if(token != null && username != null && email != null){
            return User(token, username, email)
        }
        return null
    }

    override fun isLoggedIn() = getUser() != null

    override fun clear() {
        pref.edit {
            clear()
        }
    }
}