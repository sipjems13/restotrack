package com.res.restotrack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CuisineAdapter(
    private val context: Context,
    private val cuisines: List<Cuisine>
) : BaseAdapter() {

    override fun getCount(): Int = cuisines.size
    override fun getItem(position: Int): Any = cuisines[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_cuisine, parent, false)

        val nameText = view.findViewById<TextView>(R.id.cuisineName)
        val priceText = view.findViewById<TextView>(R.id.priceRange)

        val cuisine = cuisines[position]

        nameText.text = cuisine.name
        priceText.text = "Price: ${cuisine.priceRange}"

        return view
    }
}
