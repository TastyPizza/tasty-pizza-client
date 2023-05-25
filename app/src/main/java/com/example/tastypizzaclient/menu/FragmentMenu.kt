package com.example.tastypizzaclient.menu

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.activity.PizzaDetailsActivity
import com.example.tastypizzaclient.model.MenuItem
import com.example.tastypizzaclient.service.AuthService
import com.example.tastypizzaclient.service.MenuService
import kotlinx.coroutines.launch

class FragmentMenu : Fragment() {
    private val menuService: MenuService = MenuService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_menu, container, false)


//        val menuItems = listOf(
//            MenuItem("Название 1", "Описание 1", "Цена 1"),
//            MenuItem("Название 2", "Описание 2", "Цена 2"),
//            MenuItem("Название 3", "Описание 3", "Цена 3")
//        )

        menuService.getMenuItems { menuItems ->
            println("Menu итемы: $menuItems")
            inflateMenuItems(rootView, menuItems)
        }

        return rootView
    }


    fun inflateMenuItems(view: View?, menuItems: List<MenuItem>) {
        val inflater = LayoutInflater.from(requireContext())
        val layout = view?.findViewById<LinearLayout>(R.id.menu_layout)

        layout?.removeAllViews()

        for (i in menuItems.indices) {
            val menuItem = menuItems[i]
            val itemView = inflater.inflate(R.layout.menu_item, null)

            itemView.findViewById<TextView>(R.id.menu_item_title).text = menuItem.title
            itemView.findViewById<TextView>(R.id.menu_item_description).text = menuItem.description
            itemView.findViewById<TextView>(R.id.menu_item_price).text = menuItem.price



            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (i != 0) {
                layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.menu_item_margin_top)
            }

            itemView.layoutParams = layoutParams

            layout?.addView(itemView)

            itemView.setOnClickListener {
                println("нажали на "+itemView.id)
                openPizzaDetailsActivity(menuItem)
            }
        }
    }

    private fun openPizzaDetailsActivity(menuItem: MenuItem) {

        val intent = Intent(requireContext(), PizzaDetailsActivity::class.java)
        intent.putExtra("menuItem", menuItem)
        startActivity(intent)
    }


    companion object {
        @JvmStatic
        fun newInstance() = FragmentMenu()

    }
}