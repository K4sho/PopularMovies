package ru.skillbranch.searchmovie.presentation.view_models


import androidx.lifecycle.ViewModel
import ru.skillbranch.searchmovie.data.PrefManager

class ProfileViewModel : ViewModel() {
    fun addPairToPrefs(key: String, value: String) {
        PrefManager.addPairToPrefs(key, value)
    }

    fun getValue(key: String): String {
        return PrefManager.getValueByKeyInPrefs(key)
    }
}