package com.example.tastypizzaclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tastypizzaclient.contacts.FragmentContacts
import com.example.tastypizzaclient.databinding.ActivityMainBinding
import com.example.tastypizzaclient.menu.FragmentMenu
import com.example.tastypizzaclient.orders.FragmentOrders
import com.example.tastypizzaclient.profile.fragment.ProfileFragment
import com.example.tastypizzaclient.profile.fragment.LoginFragment
import com.example.tastypizzaclient.profile.fragment.RegisterFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val loginFragment = LoginFragment.newInstance()
    val registerFragment = RegisterFragment.newInstance()
    val menuFragment = FragmentMenu.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    val ordersFragment = FragmentOrders.newInstance()
    val contactsFragment = FragmentContacts.newInstance()
    val fragmentManager = supportFragmentManager

    var fragList = mutableListOf(
        menuFragment,
        profileFragment,
        ordersFragment,
        contactsFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(fragList[0])
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> replaceFragment(fragList[0])
                R.id.profile -> replaceFragment(loginFragment)
                R.id.basket -> replaceFragment(fragList[2])
                R.id.contacts -> replaceFragment(fragList[3])
                else -> {
                }
            }
            true
        }

    }


    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}