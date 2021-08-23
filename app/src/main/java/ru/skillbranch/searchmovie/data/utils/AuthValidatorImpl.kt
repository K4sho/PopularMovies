package ru.skillbranch.searchmovie.data.utils

import java.util.regex.Pattern

class AuthValidatorImpl : AuthValidator {
    private val phonePattern = Pattern.compile("""^\+\d((\d{3})|(\(\d{3}\)))\d{3}[-]?\d{2}[-]?\d{2}$""")
    private val namePattern = Pattern.compile("^[a-zA-Z]*\$")
    private val passwordPattern = Pattern.compile("^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}\$")
    private val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun isEmailValid(email: String): Boolean {
        return email.isNotBlank() && emailPattern.matcher(email).matches()
    }

    override fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && passwordPattern.matcher(password).matches()
    }

    override fun isNameValid(name: String): Boolean {
        return name.isNotBlank() && namePattern.matcher(name).matches()
    }

    override fun isPhoneValid(phone: String): Boolean {
        return phone.isNotBlank() && phonePattern.matcher(phone).matches()
    }
}