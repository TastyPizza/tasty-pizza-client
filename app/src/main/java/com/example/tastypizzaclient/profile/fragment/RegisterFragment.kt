package com.example.tastypizzaclient.profile.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tastypizzaclient.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class RegisterFragment : Fragment() {
    private lateinit var emailInput: TextInputLayout
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailInput = view.findViewById(R.id.email_input_sign_up)
        val loginButton  = view.findViewById<Button>(R.id.registration_button_sign_up)
        loginButton.setOnClickListener {
            val email = emailInput.editText?.text.toString()
            println(email)
        }
        val picker = view.findViewById<Button>(R.id.show_date_picker_button)
//        picker.setOnClickListener {
//            val currentDate = Calendar.getInstance()
//            val year = currentDate.get(Calendar.YEAR)
//            val month = currentDate.get(Calendar.MONTH)
//            val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)
//
//            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
//                val selectedDate = "$selectedDayOfMonth.${selectedMonth + 1}.$selectedYear"
//            }, year, month, dayOfMonth)
//
//            datePickerDialog.show()
//        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment()

    }
}