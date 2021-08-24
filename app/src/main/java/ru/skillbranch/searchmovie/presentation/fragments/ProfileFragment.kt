package ru.skillbranch.searchmovie.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.skillbranch.searchmovie.R
import ru.skillbranch.searchmovie.presentation.view_models.MoviesViewModel
import ru.skillbranch.searchmovie.presentation.view_models.ProfileState
import ru.skillbranch.searchmovie.presentation.view_models.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var exitButton: Button
    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var userNameEditText: EditText
    private lateinit var userPasswordEditText: EditText
    private lateinit var userEmailEditText: EditText
    private lateinit var userPhoneEditText: EditText
    private lateinit var navController: NavController

    private lateinit var profileViewModel: ProfileViewModel
    private val profileObserver = Observer { state: ProfileState ->
        userNameTextView.text = state.name
        userEmailTextView.text = state.email
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitButton = view.findViewById(R.id.button_user_profile_exit)
        userNameTextView = view.findViewById(R.id.tv_user_profile_name)
        userEmailTextView = view.findViewById(R.id.tv_user_profile_email)
        userNameEditText = view.findViewById(R.id.et_user_profile_editable_name)
        userPasswordEditText = view.findViewById(R.id.et_user_profile_editable_password)
        userEmailEditText = view.findViewById(R.id.et_user_profile_editable_email)
        userPhoneEditText = view.findViewById(R.id.et_user_profile_editable_phone)
        navController = view.findNavController()
        profileViewModel.state.observe(requireActivity(), profileObserver)
        userNameEditText.setText(profileViewModel.getValue(ProfileViewModel.USER_NAME))
        userPasswordEditText.setText(profileViewModel.getValue(ProfileViewModel.USER_PASSWORD))
        userEmailEditText.setText(profileViewModel.getValue(ProfileViewModel.USER_EMAIL))
        userPhoneEditText.setText(profileViewModel.getValue(ProfileViewModel.USER_PHONE))

        userNameEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                profileViewModel.setName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        userPasswordEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                profileViewModel.setPassword(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        userEmailEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                profileViewModel.setEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        userPhoneEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                profileViewModel.setPhone(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        exitButton.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_logInFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}