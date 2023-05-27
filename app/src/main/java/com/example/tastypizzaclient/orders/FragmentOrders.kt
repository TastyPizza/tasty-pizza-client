package com.example.tastypizzaclient.orders

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.activity.PizzaDetailsActivity
import com.example.tastypizzaclient.model.CartItem
import com.example.tastypizzaclient.model.MenuItem
import com.example.tastypizzaclient.model.request.CreateOrderRequest
import com.example.tastypizzaclient.model.request.OrderItemDto
import com.example.tastypizzaclient.service.MenuService

class FragmentOrders : Fragment(), OrderUpdateListener {
    private val menuService: MenuService = MenuService()
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private var totalCount: Int = 0
    private var totalPrice: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        val linearLayoutItems = view.findViewById<LinearLayout>(R.id.linearLayout_items)
        val buttonCheckout = view.findViewById<Button>(R.id.button_checkout)


        //делаем заказ
        buttonCheckout.setOnClickListener {



            val listOfOrderItemDto = mutableListOf<OrderItemDto>()

            for (cartItem in cartItems) {
                val orderItemDto = OrderItemDto(
                    menuItemOptionId = cartItem.menuOptionId,
                    count = cartItem.count
                )
                listOfOrderItemDto.add(orderItemDto)
            }

            val createOrderRequest = CreateOrderRequest(
                clientId = 5,
                restaurantId = 1,
                listOfOrderItemDto = listOfOrderItemDto
            )


            if (MainActivity.accessToken == "") showAlertDialog(this.requireContext(),"Ошибка", "Вы должны быть авторизированы для совершения заказа")
            else if (!cartItems.isEmpty()){
                menuService.createOrder(createOrderRequest) { result ->
                    val orderId = result.first
                    val statusCode = result.second

                    if (statusCode == 200) {
                        // Запрос выполнен успешно
                        Log.d("Order", "Заказ создан. ID заказа: $orderId")
                        showAlertDialog(this.requireContext(),"Успех", "Заказ создан. ID заказа: $orderId")

                    } else {
                        // Обработка ошибки
                        Log.e("Order", "Не удалось создать заказ. Код ответа: $statusCode")
                        showAlertDialog(this.requireContext(),"Ошибка", "Не удалось создать заказ. Код ответа: $statusCode")
                    }
                }
            } else showAlertDialog(this.requireContext(),"Ошибка", "Корзина пуста")

        }


        return view
    }

    override fun onResume() {
        super.onResume()
        updateCartItems()
        calculateCountAndTotalPrice()
        changeTotalCountPriceView()
    }

    fun changeTotalCountPriceView(){
        val totalCountPriceView = view?.findViewById<TextView>(R.id.textView_summary)
        totalCountPriceView?.text =  "$totalCount товаров на $totalPrice рублей"
    }

    override fun onOrderUpdated(item: CartItem) {

        val existingItem = cartItems.find { it.menuItemId == item.menuItemId && it.menuOptionId == item.menuItemId}
        if (existingItem == null) {
            cartItems.add(item)
            println("Делаем что-то во фрагменте, когда у нас добавился элемент в корзину")
        }
        println(cartItems)
    }

    fun calculateCountAndTotalPrice(){
        totalCount = 0
        totalPrice = 0
        for (item in cartItems){
            totalCount+=item.count
            totalPrice+=item.price
        }
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

    private fun updateCartItems() {
        val inflater = LayoutInflater.from(requireContext())
        val linearLayoutItems = view?.findViewById<LinearLayout>(R.id.linearLayout_items)
        linearLayoutItems?.removeAllViews()

        for (item in cartItems) {
            val cartItemView = inflater.inflate(R.layout.cart_item, linearLayoutItems, false)



            val buttonIncrease: Button = cartItemView.findViewById(R.id.button_increase_quantity)
            val buttonDecrease: Button = cartItemView.findViewById(R.id.button_decrease_quantity)
            val dishTitleTextView = cartItemView.findViewById<TextView>(R.id.textView_dish_title)
            val dishDescriptionTextView = cartItemView.findViewById<TextView>(R.id.textView_dish_description)
            val dishTypeView = cartItemView.findViewById<TextView>(R.id.textView_dish_type)
            val quantityTextView = cartItemView.findViewById<TextView>(R.id.textView_quantity)
            val priceTextView = cartItemView.findViewById<TextView>(R.id.item_price)


            var type: String = ""
            when (item.type){
                "PIZZA" -> {
                    type = "Пицца"
                }
                "SNACK" -> {
                    type = "Закуска"
                }
                "DRINK" -> {
                    type = "Напиток"
                }
                "DESSERT" -> {
                    type = "Десерт"
                }
                "SAUCE" -> {
                    type = "Соус"
                }
                "OTHER" -> {
                    type = "Неведомое нечто"
                }
            }


            dishTitleTextView.text = item.title
            dishDescriptionTextView.text = item.description
            dishTypeView.text = type
            quantityTextView.text = item.count.toString()
            priceTextView.text = item.price.toString() + " ₽"

            buttonIncrease.setOnClickListener {
                item.count++
                calculateCountAndTotalPrice()
                changeTotalCountPriceView()
                updateCartItems()
            }

            buttonDecrease.setOnClickListener {
                item.count--
                if (item.count == 0) cartItems.remove(item)
                calculateCountAndTotalPrice()
                changeTotalCountPriceView()
                updateCartItems()
            }


            linearLayoutItems?.addView(cartItemView)
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = FragmentOrders()
    }
}