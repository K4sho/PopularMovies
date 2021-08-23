package ru.skillbranch.searchmovie.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.presentation.view_models.ProfileViewModel

class LogInFragment : Fragment() {
    private lateinit var userNameEditText: EditText
    private lateinit var userPasswordEditText: EditText
    private lateinit var userButton: Button
    private lateinit var navController: NavController

    private var userName: String = ""

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userNameEditText = view.findViewById(R.id.log_in_name_edit_text)
        userPasswordEditText = view.findViewById(R.id.log_in_password_edit_text)
        userButton = view.findViewById(R.id.log_in_user_button)
        navController = view.findNavController()

        userButton.setOnClickListener {
            userName = userNameEditText.text.toString()
            if (profileViewModel.getValue(ProfileViewModel.USER_NAME) == "" ||
                profileViewModel.getValue(ProfileViewModel.USER_NAME) != userName) {
                userNameEditText.text.clear()
                userPasswordEditText.text.clear()
                profileViewModel.clearProfile()
                profileViewModel.setName(userName)
                profileViewModel.setPassword(userPasswordEditText.text.toString())
                userNameEditText.text.clear()
                userPasswordEditText.text.clear()

                navController.navigate(R.id.action_logInFragment_to_moviesFragment)
            }
            else if (profileViewModel.getValue(ProfileViewModel.USER_NAME) == userName) {
                if (userPasswordEditText.text.toString() !=
                    profileViewModel.getValue(ProfileViewModel.USER_PASSWORD)) {
                    Toast.makeText(requireContext(),
                        INVALID_PASSWORD_MESSAGE, Toast.LENGTH_SHORT).show()
                }
                else {
                    userNameEditText.text.clear()
                    userPasswordEditText.text.clear()
                    navController.navigate(R.id.action_logInFragment_to_moviesFragment)
                }
            }
        }
    }

    companion object {
        const val INVALID_PASSWORD_MESSAGE: String = "Неверный пароль! Попробуйте ещё раз!"
    }
}