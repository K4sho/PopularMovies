package ru.skillbranch.searchmovie.data.local

import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import ru.skillbranch.searchmovie.App

object PrefManager {
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private const val sharedPrefsFile: String = "movies_db_pref_file"

    internal val preferences = EncryptedSharedPreferences.create(
        sharedPrefsFile,
        mainKeyAlias,
        App.applicationContext(),
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