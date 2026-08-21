package com.example.androidapp2.data

import android.content.Context
import com.example.androidapp2.R
import com.example.androidapp2.model.Coffee
import org.json.JSONArray

/**
 * Loads coffee menu information from the local JSON resource.
 */
object CoffeeRepository {

    /**
     * Reads coffee_data.json and converts each JSON object
     * into a Coffee object used by the application.
     */
    fun loadCoffees(context: Context): List<Coffee> {

        val coffeeList = mutableListOf<Coffee>()

        // Read the complete JSON file from res/raw.
        val jsonText = context.resources
            .openRawResource(R.raw.coffee_data)
            .bufferedReader()
            .use { it.readText() }

        val jsonArray = JSONArray(jsonText)

        // Convert every JSON record into a Coffee object.
        for (index in 0 until jsonArray.length()) {

            val coffeeObject = jsonArray.getJSONObject(index)

            val coffee = Coffee(
                id = coffeeObject.getInt("id"),
                name = coffeeObject.getString("name"),
                description = coffeeObject.getString("description"),
                price = coffeeObject.getDouble("price"),
                imageName = coffeeObject.getString("imageName")
            )

            coffeeList.add(coffee)
        }

        return coffeeList
    }
}