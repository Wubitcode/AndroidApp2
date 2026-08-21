package com.example.androidapp2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2.R
import com.example.androidapp2.model.CartItem
import java.util.Locale

/**
 * RecyclerView adapter responsible for displaying products
 * currently stored in the shopping cart.
 *
 * Each row shows the coffee name, selected quantity,
 * unit price, and calculated subtotal.
 *
 * @param cartItems list of products currently in the cart.
 */
class CartAdapter(
    private val cartItems: List<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    /**
     * Holds references to the views displayed for one cart item.
     */
    class CartViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val coffeeNameTextView: TextView =
            itemView.findViewById(R.id.cartCoffeeNameTextView)

        val quantityTextView: TextView =
            itemView.findViewById(R.id.cartQuantityTextView)

        val unitPriceTextView: TextView =
            itemView.findViewById(R.id.cartUnitPriceTextView)

        val subtotalTextView: TextView =
            itemView.findViewById(R.id.cartSubtotalTextView)
    }

    /**
     * Creates the visual layout used for each cart item.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cart, parent, false)

        return CartViewHolder(view)
    }

    /**
     * Binds one CartItem object to its visible RecyclerView row.
     */
    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {

        val cartItem = cartItems[position]

        holder.coffeeNameTextView.text =
            cartItem.coffee.name

        holder.quantityTextView.text =
            "Quantity: ${cartItem.quantity}"

        holder.unitPriceTextView.text =
            String.format(
                Locale.CANADA,
                "Price: $%.2f",
                cartItem.coffee.price
            )

        holder.subtotalTextView.text =
            String.format(
                Locale.CANADA,
                "Subtotal: $%.2f",
                cartItem.subtotal()
            )
    }

    /**
     * Returns the total number of unique products displayed in the cart.
     */
    override fun getItemCount(): Int {
        return cartItems.size
    }
}