package com.example.tastypizzaclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tastypizzaclient.contacts.FragmentContacts
import com.example.tastypizzaclient.databinding.ActivityMainBinding
import com.example.tastypizzaclient.menu.FragmentMenu
import com.example.tastypizzaclient.orders.FragmentOrders
import com.example.tastypizzaclient.profile.fragment.FragmentProfile
import com.example.tastypizzaclient.profile.fragment.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragList = listOf(
        FragmentMenu.newInstance(),
        FragmentProfile.newInstance(),
        FragmentOrders.newInstance(),
        FragmentContacts.newInstance(),
        LoginFragment.newInstance()
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(fragList[0])
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> replaceFragment(fragList[0])
                R.id.profile -> replaceFragment(fragList[4])
                R.id.basket -> replaceFragment(fragList[2])
                R.id.contacts -> replaceFragment(fragList[3])
                else -> {
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}