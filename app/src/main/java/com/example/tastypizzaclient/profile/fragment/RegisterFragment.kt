package com.example.tastypizzaclient.profile.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.model.request.RegisterRequest
import com.example.tastypizzaclient.retrofit.api.AuthApi
import com.example.tastypizzaclient.util.Validator
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterFragment : Fragment() {
    private lateinit var emailInput: TextInputLayout
    private lateinit var nameInput: TextInputLayout
    private lateinit var surnameInput: TextInputLayout
    private lateinit var phoneNumberInput: TextInputLayout
    private lateinit var picker: TextInputLayout
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private lateinit var dateForModel: String
    private var validator: Validator = Validator()
    private var emailValid: Boolean = false
    private var nameValid: Boolean = false
    private var surnameValid: Boolean = false
    private var phoneNumberValid: Boolean = false
    private var pickerValid: Boolean = false
    private var genderValid: Boolean = false

    private val mainActivity: MainActivity by lazy { requireActivity() as MainActivity }

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        emailInput = view.findViewById(R.id.email_input_sign_up)
        nameInput = view.findViewById(R.id.firstname_input_sign_up)
        surnameInput = view.findViewById(R.id.lastname_input_sign_up)
        phoneNumberInput = view.findViewById(R.id.phone_number_input_sign_up)
        picker = view.findViewById(R.id.show_date_picker_button)
        genderRadioGroup = view.findViewById(R.id.gender_radio_group)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://158.160.23.54:21400/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val authApi = retrofit.create(AuthApi::class.java)

        validationForm()

        picker.editText?.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showDatePickerDialog()
                true
            } else {
                false
            }
        }

        var isMale = false
        genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.male_radio_button -> {
                    isMale = true
                    genderValid = true
                    checkValid()
                }
                R.id.female_radio_button -> {
                    isMale = false
                    genderValid = true
                    checkValid()
                }
            }
        }


        val registerDTO = RegisterRequest(
            emailInput.editText?.text.toString(),
            isMale,
            nameInput.editText?.text.toString(),
            surnameInput.editText?.text.toString(),
            phoneNumberInput.editText?.text.toString(),
            picker.editText?.text.toString()
        )

        registerButton = view.findViewById(R.id.registration_button_sign_up)
        registerButton.setOnClickListener {

        }
        registerButton.isEnabled = false

        loginButton = view.findViewById(R.id.authorization_button_sign_up)
        loginButton.setOnClickListener {
            mainActivity.replaceFragment(mainActivity.loginFragment)
        }

        return view
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(requireContext())

        datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
            var monthForModel = (month + 1).toString()
            var dayForModel = dayOfMonth.toString()
            if (monthForModel.toInt() < 10) {
                monthForModel = "0${monthForModel}"
            }
            if (dayForModel.toInt() < 10) {
                dayForModel = "0${dayForModel}"
            }
            val selectedDate = "${dayForModel}-${monthForModel}-${year}"
            dateForModel = "${year}-${monthForModel}-${dayForModel}"
            picker.editText?.setText(selectedDate)
        }
        datePicker.show()
    }

    private fun validationForm() {
        phoneNumberInput.editText?.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val pickerWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    pickerValid = true
                } else {
                    pickerValid = false
                    picker.error = "Введите дату!"
                }
                checkValid()
            }
        }
        picker.editText?.addTextChangedListener(pickerWatcher)

        val nameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && validator.isValidName(s.toString())) {
                    nameValid = true
                    nameInput.error = null
                } else {
                    nameValid = false
                    nameInput.error = "Имя не корректное"
                }
                checkValid()
            }
        }
        nameInput.editText?.addTextChangedListener(nameWatcher)

        val surnameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && validator.isValidName(s.toString())) {
                    surnameValid = true
                    surnameInput.error = null
                } else {
                    surnameValid = false
                    surnameInput.error = "Фамилия не корректная"
                }
                checkValid()
            }
        }
        surnameInput.editText?.addTextChangedListener(surnameWatcher)

        val emailWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!validator.isValidEmail(s.toString())) {
                    emailValid = false
                    emailInput.error = "Введен не корректный email"
                } else {
                    emailValid = true
                    emailInput.error = null
                }
                checkValid()

            }
        }
        emailInput.editText?.addTextChangedListener(emailWatcher)
        //TODO надо пофиксить номера телефонов и сделать более адекватную проверку
        val phoneWatch = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length == 15) {
                    phoneNumberValid = true
                    phoneNumberInput.error = null
                } else {
                    phoneNumberValid = false
                    if (s != null && s.length > 15) {
                        s.delete(15, s.length)
                    } else {
                        phoneNumberInput.error = "Введен не корректный номер телефона"
                    }
                }
                checkValid()
            }
        }
        phoneNumberInput.editText?.addTextChangedListener(phoneWatch)
    }

    private fun checkValid() {
        registerButton.isEnabled =
            emailValid && nameValid && surnameValid && phoneNumberValid && pickerValid && genderValid
    }


    override fun onDestroyView() {
        emailValid = false
        nameValid = false
        surnameValid = false
        phoneNumberValid = false
        pickerValid = false
        Log.d("Дестрой", "${registerButton.isEnabled}")
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }


}