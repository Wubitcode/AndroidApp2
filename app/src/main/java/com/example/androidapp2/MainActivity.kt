package com.example.androidapp2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2.adapter.CoffeeAdapter
import com.example.androidapp2.data.CoffeeRepository

/**
 * Main activity for the Tim Hortons coffee application.
 *
 * This screen displays the available coffee products in a RecyclerView.
 * Coffee information is loaded from a local JSON resource through
 * CoffeeRepository.
 *
 * When the user selects a coffee, the selected product information is
 * passed to CoffeeDetailActivity using Intent extras.
 */
class MainActivity : AppCompatActivity() {

    /**
     * RecyclerView responsible for displaying the list of coffee products.
     */
    private lateinit var coffeeRecyclerView: RecyclerView

    /**
     * Initializes the main coffee menu when the activity is created.
     *
     * @param savedInstanceState previously saved activity state, if available.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this activity to its XML layout.
        setContentView(R.layout.activity_main)

        // Obtain a reference to the RecyclerView defined in activity_main.xml.
        coffeeRecyclerView = findViewById(R.id.coffeeRecyclerView)

        /*
         * Display the coffee products vertically.
         *
         * LinearLayoutManager manages how RecyclerView items are positioned
         * and allows the user to scroll through the coffee menu.
         */
        coffeeRecyclerView.layoutManager = LinearLayoutManager(this)

        /*
         * Load coffee product information from coffee_data.json.
         *
         * CoffeeRepository converts each JSON record into a Coffee object
         * that can be displayed by the RecyclerView adapter.
         */
        val coffees = CoffeeRepository.loadCoffees(this)

        /*
         * Connect the coffee data to the RecyclerView.
         *
         * When the user selects a coffee card or taps View Details,
         * the selected product information is passed to
         * CoffeeDetailActivity.
         */
        coffeeRecyclerView.adapter =
            CoffeeAdapter(coffees) { selectedCoffee ->

                /*
                 * Create an Intent that opens CoffeeDetailActivity.
                 *
                 * The selected coffee's properties are passed as extras so
                 * the detail screen can display the correct product.
                 */
                val detailIntent =
                    Intent(this, CoffeeDetailActivity::class.java).apply {

                        putExtra(
                            CoffeeDetailActivity.EXTRA_COFFEE_ID,
                            selectedCoffee.id
                        )

                        putExtra(
                            CoffeeDetailActivity.EXTRA_COFFEE_NAME,
                            selectedCoffee.name
                        )

                        putExtra(
                            CoffeeDetailActivity.EXTRA_COFFEE_DESCRIPTION,
                            selectedCoffee.description
                        )

                        putExtra(
                            CoffeeDetailActivity.EXTRA_COFFEE_PRICE,
                            selectedCoffee.price
                        )

                        putExtra(
                            CoffeeDetailActivity.EXTRA_COFFEE_IMAGE_NAME,
                            selectedCoffee.imageName
                        )
                    }

                // Open the detail screen for the selected coffee.
                startActivity(detailIntent)
            }
    }
}