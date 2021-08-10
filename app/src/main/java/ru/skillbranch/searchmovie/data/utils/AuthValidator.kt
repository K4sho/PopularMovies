package ru.skillbranch.searchmovie.data.utils

interface AuthValidator {
    fun isEmailValid(email: String): Boolean
    fun isPasswordValid(password: String): Boolean
    fun isNameValid(name: String): Boolean
    fun isPhoneValid(phone: String): Boolean
}