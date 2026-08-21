package com.example.androidapp2.model

/**
 * Represents one coffee product displayed in the Tim Hortons menu.
 *
 * @property id unique identifier for the coffee.
 * @property name display name of the coffee.
 * @property description short product description.
 * @property price price of one item.
 * @property imageName name of the drawable resource used for the product image.
 */
data class Coffee(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageName: String
)