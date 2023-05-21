package com.example.tastypizzaclient.profile.fragment

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
import com.example.tastypizzaclient.service.AuthService
import com.example.tastypizzaclient.util.Util
import com.google.android.material.textfield.TextInputLayout


class VerificationFragment : Fragment() {
    private val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }
    private lateinit var codeInput: TextInputLayout
    private lateinit var sendCodeButton: Button
    private val authService: AuthService = AuthService()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_verification, container, false)
        codeInput = view.findViewById(R.id.code_input_sign_in)
        sendCodeButton = view.findViewById(R.id.send_code_button_sign_in)
        validation()
        sendCodeButton.setOnClickListener {
            val code = codeInput.editText?.text.toString()
            authService.verify(MainActivity.verifyToken, code) { tokenResponse ->
                when (tokenResponse.errorMessage) {
                    "200" -> {
                        MainActivity.accessToken = tokenResponse.accessToken
                        MainActivity.refreshToken = tokenResponse.refreshToken
                        authService.getProfile(MainActivity.accessToken) { profileResponse ->
                            MainActivity.profileData = profileResponse
                            Log.d("profile!!!",profileResponse.toString())
                            mainActivity.replaceFragment(MainActivity.profileFragment)
                        }
                    }
                    "400" -> {
                        Util.showErrorDialog(
                            requireContext(),
                            "Вы ввели не верный код, попробуйте снова"
                        )
                    }
                    else -> {
                        Util.showErrorDialog(requireContext(), "Произошло что-то не предвиденное")
                    }
                }
            }
        }
        return view
    }

    private fun validation() {
        val codeWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length > 4) {
                    s.delete(4, s.length)
                }
            }
        }
        codeInput.editText?.addTextChangedListener(codeWatcher)
    }

    companion object {
        @JvmStatic
        fun newInstance() = VerificationFragment()

    }
}