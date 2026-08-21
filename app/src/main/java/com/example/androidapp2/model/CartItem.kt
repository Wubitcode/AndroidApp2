package com.example.androidapp2.model

/**
 * Represents a coffee product that has been added to the shopping cart.
 *
 * @property coffee selected coffee product.
 * @property quantity number of that product in the cart.
 */
data class CartItem(
    val coffee: Coffee,
    var quantity: Int
) {

    /**
     * Calculates the subtotal for this cart item.
     */
    fun subtotal(): Double {
        return coffee.price * quantity
    }
}