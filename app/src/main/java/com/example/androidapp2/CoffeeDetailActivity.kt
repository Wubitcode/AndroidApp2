package com.example.androidapp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapp2.manager.CartManager
import com.example.androidapp2.model.Coffee
import java.util.Locale

/**
 * Displays detailed information about a selected coffee product.
 *
 * This activity allows the user to:
 * - Review the selected coffee's name, description, price, and image.
 * - Increase or decrease the desired quantity.
 * - Add the selected quantity to the shared shopping cart.
 * - Navigate to the cart screen to review the order.
 */
class CoffeeDetailActivity : AppCompatActivity() {

    /**
     * UI components used to display the selected coffee
     * and manage the quantity selection.
     */
    private lateinit var coffeeImageView: ImageView
    private lateinit var coffeeNameTextView: TextView
    private lateinit var coffeeDescriptionTextView: TextView
    private lateinit var coffeePriceTextView: TextView
    private lateinit var quantityTextView: TextView
    private lateinit var decreaseButton: Button
    private lateinit var increaseButton: Button
    private lateinit var addToCartButton: Button

    /**
     * Stores the quantity currently selected by the user.
     *
     * The quantity begins at one and cannot fall below one.
     */
    private var quantity = 1

    /**
     * Initializes the coffee detail screen and configures
     * all quantity and cart interactions.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this activity to its XML layout.
        setContentView(R.layout.activity_coffee_detail)

        // Connect Kotlin properties to their corresponding XML views.
        coffeeImageView = findViewById(R.id.detailCoffeeImageView)
        coffeeNameTextView = findViewById(R.id.detailCoffeeNameTextView)
        coffeeDescriptionTextView =
            findViewById(R.id.detailCoffeeDescriptionTextView)
        coffeePriceTextView =
            findViewById(R.id.detailCoffeePriceTextView)
        quantityTextView =
            findViewById(R.id.quantityTextView)
        decreaseButton =
            findViewById(R.id.decreaseQuantityButton)
        increaseButton =
            findViewById(R.id.increaseQuantityButton)
        addToCartButton =
            findViewById(R.id.addToCartButton)

        /*
         * Retrieve the selected coffee information passed from MainActivity.
         *
         * Primitive values are transferred through Intent extras and then
         * reconstructed as a Coffee object for use on this screen.
         */
        val coffeeId =
            intent.getIntExtra(EXTRA_COFFEE_ID, -1)

        val coffeeName =
            intent.getStringExtra(EXTRA_COFFEE_NAME) ?: ""

        val coffeeDescription =
            intent.getStringExtra(EXTRA_COFFEE_DESCRIPTION) ?: ""

        val coffeePrice =
            intent.getDoubleExtra(EXTRA_COFFEE_PRICE, 0.0)

        val coffeeImageName =
            intent.getStringExtra(EXTRA_COFFEE_IMAGE_NAME) ?: ""

        // Reconstruct the selected coffee from the received Intent values.
        val selectedCoffee = Coffee(
            id = coffeeId,
            name = coffeeName,
            description = coffeeDescription,
            price = coffeePrice,
            imageName = coffeeImageName
        )

        // Display the selected coffee information on the detail screen.
        displayCoffee(selectedCoffee)

        /**
         * Decreases the selected quantity by one.
         *
         * The condition prevents the quantity from falling below one.
         */
        decreaseButton.setOnClickListener {

            if (quantity > 1) {
                quantity--
                updateQuantity()
            }
        }

        /**
         * Increases the selected quantity by one.
         */
        increaseButton.setOnClickListener {

            quantity++
            updateQuantity()
        }

        /**
         * Adds the selected coffee and quantity to the shared cart.
         *
         * After the item is stored successfully, the user is taken
         * directly to CartActivity to review the order.
         */
        addToCartButton.setOnClickListener {

            // Store the selected product and quantity in CartManager.
            CartManager.addToCart(
                selectedCoffee,
                quantity
            )

            // Provide temporary confirmation that the item was added.
            Toast.makeText(
                this,
                "$quantity × ${selectedCoffee.name} added to cart",
                Toast.LENGTH_SHORT
            ).show()

            // Create an Intent to open the shopping cart screen.
            val cartIntent =
                Intent(this, CartActivity::class.java)

            // Navigate to CartActivity.
            startActivity(cartIntent)
        }
    }

    /**
     * Displays the selected coffee's information.
     *
     * The method updates the product name, description, price,
     * image, and initial quantity shown on the screen.
     *
     * @param coffee the coffee product selected from the main menu.
     */
    private fun displayCoffee(coffee: Coffee) {

        coffeeNameTextView.text = coffee.name
        coffeeDescriptionTextView.text = coffee.description

        // Format the coffee price as Canadian currency.
        coffeePriceTextView.text =
            String.format(
                Locale.CANADA,
                "$%.2f",
                coffee.price
            )

        /*
         * Locate the drawable resource whose name matches
         * the imageName stored in coffee_data.json.
         */
        val imageResourceId =
            resources.getIdentifier(
                coffee.imageName,
                "drawable",
                packageName
            )

        /*
         * Display the coffee image when the drawable exists.
         *
         * The application icon is used temporarily when a matching
         * product image has not yet been added to res/drawable.
         */
        if (imageResourceId != 0) {

            coffeeImageView.setImageResource(
                imageResourceId
            )

        } else {

            coffeeImageView.setImageResource(
                R.mipmap.ic_launcher
            )
        }

        // Display the initial selected quantity.
        updateQuantity()
    }

    /**
     * Updates the quantity value displayed to the user.
     */
    private fun updateQuantity() {

        quantityTextView.text =
            quantity.toString()
    }

    companion object {

        /**
         * Intent keys used to transfer coffee information
         * from MainActivity to CoffeeDetailActivity.
         */
        const val EXTRA_COFFEE_ID =
            "coffee_id"

        const val EXTRA_COFFEE_NAME =
            "coffee_name"

        const val EXTRA_COFFEE_DESCRIPTION =
            "coffee_description"

        const val EXTRA_COFFEE_PRICE =
            "coffee_price"

        const val EXTRA_COFFEE_IMAGE_NAME =
            "coffee_image_name"
    }
}