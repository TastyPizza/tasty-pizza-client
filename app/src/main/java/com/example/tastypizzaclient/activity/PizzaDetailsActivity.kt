package com.example.tastypizzaclient.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tastypizzaclient.R
import com.example.tastypizzaclient.model.MenuItem

class PizzaDetailsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_details)

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

            if (item.type == "PIZZA") {
                buttonSmall.visibility = View.VISIBLE
                buttonMedium.visibility = View.VISIBLE
                buttonLarge.visibility = View.VISIBLE
            } else {
                buttonSmall.visibility = View.GONE
                buttonMedium.visibility = View.GONE
                buttonLarge.visibility = View.GONE
            }

            val toggleButtonClickListener = View.OnClickListener { view ->
                when (view.id) {
                    R.id.button_small -> {
                        buttonMedium.isChecked = false
                        buttonLarge.isChecked = false
                    }
                    R.id.button_medium -> {
                        buttonSmall.isChecked = false
                        buttonLarge.isChecked = false
                    }
                    R.id.button_large -> {
                        buttonSmall.isChecked = false
                        buttonMedium.isChecked = false
                    }
                }
            }

            buttonSmall.setOnClickListener(toggleButtonClickListener)
            buttonMedium.setOnClickListener(toggleButtonClickListener)
            buttonLarge.setOnClickListener(toggleButtonClickListener)

            val buttonAddToCart = findViewById<Button>(R.id.button_add_to_cart)
            buttonAddToCart.setOnClickListener {
            }
        }
    }
}