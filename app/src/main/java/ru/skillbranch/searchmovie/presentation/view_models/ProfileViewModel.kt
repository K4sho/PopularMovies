package ru.skillbranch.searchmovie.presentation.view_models


import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.searchmovie.data.PrefManager

class ProfileViewModel : ViewModel() {
    val state: LiveData<ProfileState>
        get() = _state
    private val _state = MutableLiveData<ProfileState>().apply {
        /// Инициализируем начальным состоянием
        value = ProfileState(
            getValue(USER_NAME), getValue(USER_EMAIL),
            getValue(USER_PASSWORD), getValue(USER_PHONE)
        )
    }

    fun setName(name: String) {
        PrefManager.addPairToPrefs(USER_NAME, name)
        updateState { it.copy(name = name) }
    }

    fun setPassword(password: String) {
        PrefManager.addPairToPrefs(USER_PASSWORD, password)
        updateState { it.copy(password = password) }
    }

    fun setEmail(email: String) {
        PrefManager.addPairToPrefs(USER_EMAIL, email)
        updateState { it.copy(email = email) }
    }

    fun setPhone(phone: String) {
        PrefManager.addPairToPrefs(USER_PHONE, phone)
        updateState { it.copy(phone = phone) }
    }


    fun getValue(key: String): String {
        return PrefManager.getValueByKeyInPrefs(key)
    }

    fun clearProfile() {
        PrefManager.clearAll()
    }
    /***
     * лямбда выражение принимает в качестве аргумента текущее состояние и возвращает
     * модифицированное состояние, которое присваивается текущему состоянию
     */
    @UiThread
    private inline fun updateState(update: (currentState: ProfileState) -> ProfileState) {
        val updatedState: ProfileState = update(state.value!!)
        _state.value = updatedState
    }

    companion object {
        const val USER_NAME: String = "userName"
        const val USER_PASSWORD: String = "userPassword"
        const val USER_EMAIL: String = "userEmail"
        const val USER_PHONE: String = "userPhone"
    }
}

data class ProfileState(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)