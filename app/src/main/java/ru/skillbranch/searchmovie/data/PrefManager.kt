package ru.skillbranch.searchmovie.data

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import ru.skillbranch.searchmovie.App

object PrefManager {
    private val masterKey = MasterKey.Builder(App.applicationContext())
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private const val sharedPrefsFile: String = "movies_db_pref_file"

    val preference by lazy {
        EncryptedSharedPreferences.create(
            App.applicationContext(),
            sharedPrefsFile,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun clearAll() {
        preference.edit().clear().apply()
    }

    fun addPairToPrefs(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun getValueByKeyInPrefs(key: String): String {
        return preference.getString(key, "")!!
    }
}