package com.example.tastypizzaclient.profile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.google.android.material.textfield.TextInputLayout

class ProfileFragment : Fragment() {
    lateinit var profileEmailInput: TextInputLayout
    lateinit var profilePhoneInput: TextInputLayout
    lateinit var profileFirstNameInput: TextInputLayout
    lateinit var profileLastNameInput: TextInputLayout
    lateinit var profileDateInput: TextInputLayout


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

        profileEmailInput.editText?.setText(MainActivity.profileData.email)
        profilePhoneInput.editText?.setText(MainActivity.profileData.phoneNumber)
        profileFirstNameInput.editText?.setText(MainActivity.profileData.firstName)
        profileLastNameInput.editText?.setText(MainActivity.profileData.lastName)
        profileDateInput.editText?.setText(MainActivity.profileData.birthday)
        MainActivity.fragList[1] = MainActivity.profileFragment

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()

    }
}