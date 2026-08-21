package com.example.androidapp2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2.R
import com.example.androidapp2.model.Coffee
import java.util.Locale

/**
 * RecyclerView adapter responsible for displaying coffee products.
 *
 * @param coffeeList list of coffee products shown on the menu.
 * @param onCoffeeClick callback executed when the user chooses a coffee.
 */
class CoffeeAdapter(
    private val coffeeList: List<Coffee>,
    private val onCoffeeClick: (Coffee) -> Unit
) : RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    /**
     * Holds references to the views used by one coffee card.
     */
    class CoffeeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView =
            itemView.findViewById(R.id.coffeeImageView)

        val nameTextView: TextView =
            itemView.findViewById(R.id.coffeeNameTextView)

        val descriptionTextView: TextView =
            itemView.findViewById(R.id.coffeeDescriptionTextView)

        val priceTextView: TextView =
            itemView.findViewById(R.id.coffeePriceTextView)

        val viewDetailsButton: Button =
            itemView.findViewById(R.id.viewDetailsButton)
    }

    /**
     * Creates the layout for one coffee card.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoffeeViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_coffee, parent, false)

        return CoffeeViewHolder(view)
    }

    /**
     * Connects one Coffee object to its visible card.
     */
    override fun onBindViewHolder(
        holder: CoffeeViewHolder,
        position: Int
    ) {

        val coffee = coffeeList[position]

        holder.nameTextView.text = coffee.name
        holder.descriptionTextView.text = coffee.description

        holder.priceTextView.text =
            String.format(
                Locale.CANADA,
                "$%.2f",
                coffee.price
            )

        /*
         * Look for the drawable using the imageName stored
         * in coffee_data.json.
         */
        val imageResourceId =
            holder.itemView.context.resources.getIdentifier(
                coffee.imageName,
                "drawable",
                holder.itemView.context.packageName
            )

        /*
         * Until the real coffee images are added, use the
         * application icon as a safe placeholder.
         */
        if (imageResourceId != 0) {
            holder.imageView.setImageResource(imageResourceId)
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_launcher)
        }

        // Open the selected coffee when the button is tapped.
        holder.viewDetailsButton.setOnClickListener {
            onCoffeeClick(coffee)
        }

        // The complete card can also be tapped.
        holder.itemView.setOnClickListener {
            onCoffeeClick(coffee)
        }
    }

    /**
     * Returns the number of coffees displayed in the list.
     */
    override fun getItemCount(): Int {
        return coffeeList.size
    }
}