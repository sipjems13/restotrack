package com.res.restotrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.res.restotrack.R
import com.res.restotrack.data.Restaurant

class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.restaurantImage)
        val name: TextView = view.findViewById(R.id.restaurantName)
        val cuisine: TextView = view.findViewById(R.id.restaurantCuisine)
        val rating: TextView = view.findViewById(R.id.restaurantRating)
        val status: TextView = view.findViewById(R.id.restaurantStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        
        // Load image using Glide
        Glide.with(holder.image.context)
            .load(restaurant.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(holder.image)

        holder.name.text = restaurant.name
        holder.cuisine.text = restaurant.cuisine
        holder.rating.text = "★ ${restaurant.rating}"
        holder.status.text = restaurant.status

        holder.itemView.setOnClickListener {
            onItemClick(restaurant)
        }
    }

    override fun getItemCount() = restaurants.size
} 