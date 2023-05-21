package com.example.tastypizzaclient.profile.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.model.response.AuthResponse

import com.example.tastypizzaclient.service.AuthService
import com.example.tastypizzaclient.util.Util
import com.example.tastypizzaclient.util.Validator
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {
    private lateinit var emailInput: TextInputLayout
    private val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }
    private val authService: AuthService = AuthService()
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private var validator: Validator = Validator()


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailInput = view.findViewById(R.id.email_input_sign_in)
        loginButton = view.findViewById(R.id.authorization_button_sign_in)
        loginButton.setOnClickListener {
            val email = emailInput.editText?.text.toString()
            authService.signIn(email) { authResponse ->
                when (authResponse.errorMessage) {
                    "200" -> {
                        MainActivity.verifyToken = authResponse.jwt
                        mainActivity.replaceFragment(MainActivity.verificationFragment)
                    }
                    "404" -> {
                        Util.showErrorDialog(requireContext(), "Пользователь не найден")
                    }
                    else -> {
                        Util.showErrorDialog(requireContext(), "Произошло что-то не предвиденное")
                    }
                }
            }
        }
        registerButton = view.findViewById(R.id.registration_button_sign_in)
        loginButton.isEnabled = false
        registerButton.setOnClickListener {
            mainActivity.replaceFragment(MainActivity.registerFragment)
        }
        validationForm()
        return view
    }

    private fun validationForm() {
        val emailWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!validator.isValidEmail(s.toString())) {
                    loginButton.isEnabled = false
                    emailInput.error = "Введен не корректный email"
                } else {
                    loginButton.isEnabled = true
                    emailInput.error = null
                }

            }
        }
        emailInput.editText?.addTextChangedListener(emailWatcher)
    }

    override fun onDestroyView() {
        loginButton.isEnabled = false
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()

    }
}