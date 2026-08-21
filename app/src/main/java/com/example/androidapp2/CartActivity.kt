package com.example.androidapp2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2.adapter.CartAdapter
import com.example.androidapp2.manager.CartManager
import java.util.Locale

/**
 * Displays all coffee products currently stored in the shopping cart.
 *
 * This activity allows the user to:
 * - Review all selected coffee products.
 * - View quantities, subtotals, and the complete order total.
 * - Continue shopping without clearing the existing cart.
 * - Place the order and navigate to the confirmation screen.
 */
class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var emptyCartTextView: TextView
    private lateinit var cartTotalTextView: TextView
    private lateinit var continueShoppingButton: Button
    private lateinit var placeOrderButton: Button
    private lateinit var cartSummaryLayout: View

    /**
     * Initializes the shopping cart screen and configures
     * navigation and checkout actions.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this activity to its XML layout.
        setContentView(R.layout.activity_cart)

        // Obtain references to the views defined in activity_cart.xml.
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        emptyCartTextView = findViewById(R.id.emptyCartTextView)
        cartTotalTextView = findViewById(R.id.cartTotalTextView)
        continueShoppingButton = findViewById(R.id.continueShoppingButton)
        placeOrderButton = findViewById(R.id.placeOrderButton)
        cartSummaryLayout = findViewById(R.id.cartSummaryLayout)

        // Display cart products vertically in the RecyclerView.
        cartRecyclerView.layoutManager =
            LinearLayoutManager(this)

        // Load and display the current shopping cart contents.
        displayCart()

        /**
         * Returns the user to the coffee menu so additional
         * products can be selected.
         *
         * CartManager is intentionally not cleared here,
         * allowing previously selected products to remain
         * available when the user returns to the cart.
         */
        continueShoppingButton.setOnClickListener {

            val menuIntent =
                Intent(this, MainActivity::class.java).apply {

                    /*
                     * Return to the existing MainActivity instead
                     * of creating unnecessary duplicate menu screens.
                     */
                    flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                }

            startActivity(menuIntent)

            // Close the current cart screen after returning to the menu.
            finish()
        }

        /**
         * Completes the current order and opens the
         * order confirmation screen.
         */
        placeOrderButton.setOnClickListener {

            // Save the final total before clearing the cart.
            val orderTotal =
                CartManager.getCartTotal()

            /*
             * Create the confirmation Intent and transfer
             * the completed order total.
             */
            val confirmationIntent =
                Intent(
                    this,
                    OrderConfirmationActivity::class.java
                ).apply {

                    putExtra(
                        OrderConfirmationActivity.EXTRA_ORDER_TOTAL,
                        orderTotal
                    )
                }

            // Clear the cart only after the user places the order.
            CartManager.clearCart()

            // Open the order confirmation screen.
            startActivity(confirmationIntent)

            // Remove the completed cart screen from the activity stack.
            finish()
        }
    }

    /**
     * Loads products from CartManager and updates the cart interface.
     *
     * When the cart contains products, the RecyclerView and order
     * summary are displayed. When it is empty, an appropriate
     * empty-cart message is shown instead.
     */
    private fun displayCart() {

        val cartItems =
            CartManager.getCartItems()

        if (cartItems.isEmpty()) {

            // Hide cart content when no products have been selected.
            cartRecyclerView.visibility = View.GONE
            cartSummaryLayout.visibility = View.GONE

            // Display the empty-cart message.
            emptyCartTextView.visibility = View.VISIBLE

        } else {

            // Display the cart content and checkout controls.
            cartRecyclerView.visibility = View.VISIBLE
            cartSummaryLayout.visibility = View.VISIBLE
            emptyCartTextView.visibility = View.GONE

            // Connect the stored cart products to the RecyclerView.
            cartRecyclerView.adapter =
                CartAdapter(cartItems)

            // Calculate and display the complete order total.
            cartTotalTextView.text =
                String.format(
                    Locale.CANADA,
                    "Total: $%.2f",
                    CartManager.getCartTotal()
                )
        }
    }
}