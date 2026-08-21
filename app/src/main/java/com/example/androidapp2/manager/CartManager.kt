package com.example.androidapp2.manager

import com.example.androidapp2.model.CartItem
import com.example.androidapp2.model.Coffee

/**
 * Manages items added to the shopping cart.
 *
 * The object keyword creates one shared CartManager instance
 * that can be accessed from different activities in the app.
 */
object CartManager {

    private val cartItems = mutableListOf<CartItem>()

    /**
     * Adds a coffee to the cart.
     *
     * If the coffee already exists in the cart, its quantity
     * is increased instead of creating a duplicate item.
     */
    fun addToCart(coffee: Coffee, quantity: Int) {

        val existingItem = cartItems.find {
            it.coffee.id == coffee.id
        }

        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            cartItems.add(
                CartItem(
                    coffee = coffee,
                    quantity = quantity
                )
            )
        }
    }

    /**
     * Returns all current cart items.
     */
    fun getCartItems(): List<CartItem> {
        return cartItems
    }

    /**
     * Calculates the total price of all products in the cart.
     */
    fun getCartTotal(): Double {
        return cartItems.sumOf {
            it.subtotal()
        }
    }

    /**
     * Calculates the total number of products currently in the cart.
     */
    fun getItemCount(): Int {
        return cartItems.sumOf {
            it.quantity
        }
    }

    /**
     * Removes all products from the cart after an order is completed.
     */
    fun clearCart() {
        cartItems.clear()
    }
}