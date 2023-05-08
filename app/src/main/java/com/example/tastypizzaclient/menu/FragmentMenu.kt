package com.example.tastypizzaclient.menu

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.model.MenuItem

class FragmentMenu : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_menu, container, false)

        val menuItems = listOf(
            MenuItem("Название 1", "Описание 1", "Цена 1"),
            MenuItem("Название 2", "Описание 2", "Цена 2"),
            MenuItem("Название 3", "Описание 3", "Цена 3")
        )

        inflateMenuItems(rootView, menuItems)
//
//        val myTextView = rootView.findViewById<TextView>(R.id.menu_item_price)
//
//        myTextView.setOnClickListener {
//
//
//
//            myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (10..20).random().toFloat())
//
//        }

        return rootView
    }


    fun inflateMenuItems(view :View?, menuItems: List<MenuItem>) {
        val inflater = LayoutInflater.from(requireContext())
        val layout = view?.findViewById<LinearLayout>(R.id.menu_layout)

        for (menuItem in menuItems) {
            val itemView = inflater.inflate(R.layout.menu_item, null)

            itemView.findViewById<TextView>(R.id.menu_item_title).text = menuItem.title
            itemView.findViewById<TextView>(R.id.menu_item_description).text = menuItem.description
            itemView.findViewById<TextView>(R.id.menu_item_price).text = menuItem.price

//            itemView.findViewById<TextView>(R.id.menu_item_price).setOnClickListener {
//                // ваш обработчик нажатия
//            }

            if (layout != null) {
                layout.addView(itemView)
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = FragmentMenu()

    }
}