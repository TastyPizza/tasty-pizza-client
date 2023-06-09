package com.example.tastypizzaclient.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tastypizzaclient.R

class FragmentContacts : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentContacts()

    }
}