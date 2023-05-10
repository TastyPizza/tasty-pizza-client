package com.example.tastypizzaclient.profile.fragment

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.model.response.AuthResponse
import com.example.tastypizzaclient.retrofit.api.AuthApi
import com.example.tastypizzaclient.service.AuthService
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginFragment : Fragment() {
    private lateinit var emailInput: TextInputLayout
    private val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }
    private val authService: AuthService = AuthService()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailInput = view.findViewById(R.id.email_input_sign_in)
        val loginButton = view.findViewById<Button>(R.id.authorization_button_sign_in)
        loginButton.setOnClickListener {
            val email = emailInput.editText?.text.toString()
            val authResponse: AuthResponse = authService.signIn(email)
            print(authResponse.toString())
        }

        val registerButton = view.findViewById<Button>(R.id.registration_button_sign_in)
        registerButton.setOnClickListener {
            mainActivity.replaceFragment(mainActivity.registerFragment)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()

    }
}