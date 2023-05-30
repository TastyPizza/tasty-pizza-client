package com.example.tastypizzaclient

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tastypizzaclient.contacts.FragmentContacts
import com.example.tastypizzaclient.databinding.ActivityMainBinding
import com.example.tastypizzaclient.menu.FragmentMenu
import com.example.tastypizzaclient.model.CartItem
import com.example.tastypizzaclient.model.MenuItem
import com.example.tastypizzaclient.model.MenuItemOption
import com.example.tastypizzaclient.model.response.ProfileResponse
import com.example.tastypizzaclient.orders.FragmentOrders
import com.example.tastypizzaclient.orders.OrderUpdateListener
import com.example.tastypizzaclient.profile.fragment.ProfileFragment
import com.example.tastypizzaclient.profile.fragment.LoginFragment
import com.example.tastypizzaclient.profile.fragment.RegisterFragment
import com.example.tastypizzaclient.profile.fragment.VerificationFragment
import com.example.tastypizzaclient.service.AuthService

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var preference: SharedPreferences

    val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        currentInstance = this
        loadData()
        refreshToken()
        loadProfile()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(fragList[0])
        setOrderUpdateListener(fragList[2] as OrderUpdateListener)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> replaceFragment(fragList[0])
                R.id.profile -> replaceFragment(fragList[1])
                R.id.basket -> {replaceFragment(fragList[2]) }
                R.id.contacts -> replaceFragment(fragList[3])
                else -> {
                }
            }
            true
        }

    }




    override fun onPause() {
        saveData()
        super.onPause()
    }

    private fun saveData() {
        preference = getPreferences(MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("accessToken", accessToken)
        editor.putString("refreshToken", refreshToken)
        editor.apply()
    }

    private fun loadData() {
        preference = getPreferences(MODE_PRIVATE)
        accessToken = preference.getString("accessToken", "").toString()
        refreshToken = preference.getString("refreshToken", "").toString()
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    companion object {
        var accessToken: String = "1"
        var refreshToken: String = "2"
        var verifyToken: String = "3"
        lateinit var profileData: ProfileResponse
        private var currentInstance: MainActivity? = null
        private var orderUpdateListener: OrderUpdateListener? = null
        val loginFragment = LoginFragment.newInstance()
        val registerFragment = RegisterFragment.newInstance()
        val menuFragment = FragmentMenu.newInstance()
        val profileFragment = ProfileFragment.newInstance()
        val ordersFragment = FragmentOrders.newInstance()
        val contactsFragment = FragmentContacts.newInstance()
        val verificationFragment = VerificationFragment.newInstance()
        var fragList = mutableListOf(
            menuFragment,
            loginFragment,
            ordersFragment,
            contactsFragment
        )
        val authService: AuthService = AuthService()

        fun loadProfile() {
            authService.getProfile(accessToken) { profileResponse ->
                profileData = profileResponse
            }
        }

        fun refreshToken() {
            authService.refreshTokens(refreshToken) { tokenResponse ->
                when (tokenResponse.errorMessage) {
                    "200" -> {
                        accessToken = tokenResponse.accessToken
                        refreshToken = tokenResponse.refreshToken
                        fragList[1] = profileFragment
                    }
                    else -> {
                        fragList[1] = loginFragment
                    }
                }
            }
        }

        fun getCurrentInstance(): MainActivity? {
            return currentInstance
        }
        fun setOrderUpdateListener(listener: OrderUpdateListener) {
            orderUpdateListener = listener
        }

        fun addToCart(item: MenuItem, option: MenuItemOption) {
            println("Вызвали метод добавления в корзину")
            val cartItem: CartItem = CartItem(item.id, option.id, item.title,option.toString(),option.price,1,item.type,)
            orderUpdateListener?.onOrderUpdated(cartItem)
        }
    }
}