package ru.skillbranch.searchmovie.data.database

import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import ru.skillbranch.searchmovie.App

object PrefManager {
    private val masterKey = MasterKey.Builder(App.applicationContext())
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private const val sharedPrefsFile: String = "movies_db_pref_file"

    val preferences = EncryptedSharedPreferences.create(
        App.applicationContext(),
        sharedPrefsFile,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun clearAll() {
        preferences.edit().clear().apply()
    }

    fun isAuth(): MutableLiveData<Boolean> {
        return MutableLiveData(false)
    }

    fun setAuth(auth: Boolean): Unit {
    }
}