package com.example.tastypizzaclient.profile.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {
    private lateinit var emailInput: TextInputLayout
    private val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailInput = view.findViewById(R.id.email_input_sign_in)
        val loginButton  = view.findViewById<Button>(R.id.authorization_button_sign_in)
        loginButton.setOnClickListener {
            val email = emailInput.editText?.text.toString()
            println(email)
        }
        val registerButton  = view.findViewById<Button>(R.id.registration_button_sign_in)
        registerButton.setOnClickListener {
            switchLoginOnRegister()
        }
        return view
    }
    private fun switchLoginOnRegister(){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, mainActivity.registerFragment)
        fragmentTransaction.commit()
    }
    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()

    }
}