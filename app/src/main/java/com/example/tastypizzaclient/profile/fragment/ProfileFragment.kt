package com.example.tastypizzaclient.profile.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.set
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.util.Util
import com.google.android.material.textfield.TextInputLayout

class ProfileFragment : Fragment() {
    lateinit var profileEmailInput: TextInputLayout
    lateinit var profilePhoneInput: TextInputLayout
    lateinit var profileFirstNameInput: TextInputLayout
    lateinit var profileLastNameInput: TextInputLayout
    lateinit var profileDateInput: TextInputLayout
    lateinit var tastyCoinsText: TextView
    lateinit var layout: LinearLayout
    lateinit var exitButton: Button

    private val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileEmailInput = view.findViewById(R.id.email_profile)
        profilePhoneInput = view.findViewById(R.id.phone_number_profile)
        profileFirstNameInput = view.findViewById(R.id.firstname_profile)
        profileLastNameInput = view.findViewById(R.id.lastname_profile)
        profileDateInput = view.findViewById(R.id.date_profile)
        tastyCoinsText = view.findViewById(R.id.tasty_coins_profile_input)
        layout = view.findViewById(R.id.coins_frame)
        exitButton = view.findViewById(R.id.exit_button)

        profileEmailInput.editText?.setText(MainActivity.profileData.email)
        profilePhoneInput.editText?.setText(MainActivity.profileData.phoneNumber)
        profileFirstNameInput.editText?.setText(MainActivity.profileData.firstName)
        profileLastNameInput.editText?.setText(MainActivity.profileData.lastName)
        profileDateInput.editText?.setText(MainActivity.profileData.birthday)
        tastyCoinsText.text = MainActivity.profileData.tastyCoins.toString()
        MainActivity.fragList[1] = MainActivity.profileFragment

        layout.setOnClickListener {
            Util.showUpdateDialog(
                requireContext(),
                "В данной версии приложения, эта функция еще не доступна, но она обязательно скоро появится!"
            )
        }
        exitButton.setOnClickListener {
            MainActivity.accessToken = ""
            MainActivity.refreshToken = ""
            MainActivity.fragList[1] = MainActivity.loginFragment
            mainActivity.replaceFragment(MainActivity.loginFragment)
        }


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()

    }
}