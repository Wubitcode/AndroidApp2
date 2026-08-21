package com.example.androidapp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

/**
 * Displays confirmation after the user successfully places an order.
 *
 * The activity receives the completed order total from CartActivity
 * and provides an option to return to the main coffee menu.
 */
class OrderConfirmationActivity : AppCompatActivity() {

    private lateinit var orderTotalTextView: TextView
    private lateinit var backToMenuButton: Button

    /**
     * Initializes the confirmation screen and displays
     * the total value of the completed order.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this activity to its XML layout.
        setContentView(R.layout.activity_order_confirmation)

        // Connect Kotlin properties to their corresponding XML views.
        orderTotalTextView =
            findViewById(R.id.orderTotalTextView)

        backToMenuButton =
            findViewById(R.id.backToMenuButton)

        /*
         * Retrieve the final order total supplied by CartActivity.
         * A default value of zero is used if the value is unavailable.
         */
        val orderTotal =
            intent.getDoubleExtra(EXTRA_ORDER_TOTAL, 0.0)

        // Format and display the completed order total.
        orderTotalTextView.text =
            String.format(
                Locale.CANADA,
                "Order Total: $%.2f",
                orderTotal
            )

        /**
         * Returns the user to the main coffee menu.
         *
         * FLAG_ACTIVITY_CLEAR_TOP removes the detail and cart
         * activities above MainActivity from the activity stack.
         */
        backToMenuButton.setOnClickListener {

            val menuIntent =
                Intent(this, MainActivity::class.java).apply {

                    flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                }

            startActivity(menuIntent)
            finish()
        }
    }

    companion object {

        /**
         * Intent key used to transfer the completed order total
         * from CartActivity to the confirmation screen.
         */
        const val EXTRA_ORDER_TOTAL =
            "order_total"
    }
}