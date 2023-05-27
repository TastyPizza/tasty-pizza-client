package com.example.tastypizzaclient.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.model.MenuItem
import com.example.tastypizzaclient.model.MenuItemOption
import com.example.tastypizzaclient.service.MenuService


class PizzaDetailsActivity : AppCompatActivity(){
    private val menuService: MenuService = MenuService()



    override fun onCreate(savedInstanceState: Bundle?) {
        println("Вызывается onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_details)



        val menuItemOptions: List<MenuItemOption> = MenuService.getMenuItemOptions()
        println("опшаныыыы: "+ menuItemOptions)

        val menuItem = intent.getSerializableExtra("menuItem") as MenuItem
        val imageViewDish = findViewById<ImageView>(R.id.imageView_dish)
        val textViewTitle = findViewById<TextView>(R.id.textView_title)
        val textViewDescription = findViewById<TextView>(R.id.textView_description)
        val buttonSmall = findViewById<ToggleButton>(R.id.button_small)
        val buttonMedium = findViewById<ToggleButton>(R.id.button_medium)
        val buttonLarge = findViewById<ToggleButton>(R.id.button_large)

        menuItem?.let { item ->
            imageViewDish.setImageResource(R.drawable.dodster)
            textViewTitle.text = item.title
            textViewDescription.text = item.description

            buttonSmall.visibility = View.GONE
            buttonMedium.visibility = View.GONE
            buttonLarge.visibility = View.GONE


            val availableOptions: List<MenuItemOption> = getMenuItemOptions(menuItemOptions, item.id)

            when (availableOptions.size){
                1 -> {
                    buttonSmall.text=availableOptions[0].name
                    buttonSmall.visibility = View.VISIBLE
                }
                2 -> {
                    buttonSmall.text=availableOptions[0].name
                    buttonSmall.visibility = View.VISIBLE
                    buttonMedium.text=availableOptions[1].name
                    buttonMedium.visibility = View.VISIBLE

                }
                3 -> {
                    buttonSmall.text=availableOptions[0].name
                    buttonSmall.visibility = View.VISIBLE
                    buttonMedium.text=availableOptions[1].name
                    buttonMedium.visibility = View.VISIBLE
                    buttonLarge.text=availableOptions[2].name
                    buttonLarge.visibility = View.VISIBLE
                }
            }

            val buttonAddToCart = findViewById<Button>(R.id.button_add_to_cart)

            val toggleButtonClickListener = View.OnClickListener { view ->
                when (view.id) {
                    R.id.button_small -> {
                        buttonMedium.isChecked = false
                        buttonLarge.isChecked = false
                        if (buttonSmall.isChecked) {
                            textViewDescription.text = availableOptions[0].toString()
                            buttonAddToCart.text = "Добавить в корзину за ${availableOptions[0].price} ₽"
                        }
                        else {
                            textViewDescription.text = item.description
                            buttonAddToCart.text = "Добавить в корзину"
                        }
                    }
                    R.id.button_medium -> {
                        buttonSmall.isChecked = false
                        buttonLarge.isChecked = false
                        if (buttonMedium.isChecked) {
                            textViewDescription.text = availableOptions[1].toString()
                            buttonAddToCart.text = "Добавить в корзину за ${availableOptions[1].price} ₽"
                        }
                        else {
                            textViewDescription.text = item.description
                            buttonAddToCart.text = "Добавить в корзину"
                        }
                    }
                    R.id.button_large -> {
                        buttonSmall.isChecked = false
                        buttonMedium.isChecked = false
                        if (buttonLarge.isChecked) {
                            textViewDescription.text = availableOptions[2].toString()
                            buttonAddToCart.text = "Добавить в корзину за ${availableOptions[2].price} ₽"
                        }
                        else {
                            textViewDescription.text = item.description
                            buttonAddToCart.text = "Добавить в корзину"
                        }
                    }
                }

                when (availableOptions.size){
                    1 -> {
                        buttonSmall.text=availableOptions[0].name
                    }
                    2 -> {
                        buttonSmall.text=availableOptions[0].name
                        buttonMedium.text=availableOptions[1].name

                    }
                    3 -> {
                        buttonSmall.text=availableOptions[0].name
                        buttonMedium.text=availableOptions[1].name
                        buttonLarge.text=availableOptions[2].name
                    }
                }
            }

            buttonSmall.setOnClickListener(toggleButtonClickListener)
            buttonMedium.setOnClickListener(toggleButtonClickListener)
            buttonLarge.setOnClickListener(toggleButtonClickListener)


            buttonAddToCart.setOnClickListener {
                println("Нажали кнопку добавить в корзину "+item)


                var selected: Int = -1
                if (buttonSmall.isVisible && buttonSmall.isChecked) selected = 0
                if (buttonMedium.isVisible && buttonMedium.isChecked) selected = 1
                if (buttonLarge.isVisible && buttonLarge.isChecked) selected = 2

                if (selected == -1){
                    showAlertDialog(this,"Ошибка", "Нужно выбрать опцию")
                } else {

                    menuService.checkMenuItem(availableOptions[selected].id, 1) { statusCode ->
                        if (statusCode == 200) {
                            println("Запрос выполнен успешно")
                            MainActivity.addToCart(item,availableOptions[selected])
                            showAlertDialog(this,"Успех", "Добавлено в корзину")
                        } else {

                            showAlertDialog(this,"Ошибка","Недостаточно ингредиентов для приготовления этого блюда в даный момент. Пожалуйста, выберите что-то другое")
                        }
                    }

//

                }

            }
        }
    }



    fun getMenuItemOptions(options: List<MenuItemOption>, menuItemId: Int): List<MenuItemOption>{
        return options.filter { it.menuItemId == menuItemId }
    }

    fun showAlertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}