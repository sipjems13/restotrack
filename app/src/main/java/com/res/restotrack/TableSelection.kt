package com.res.restotrack

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView

class TableSelection : Activity() {
    private lateinit var tableGrid: GridLayout
    private var selectedTable = -1
    private lateinit var proceedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_selection)

        tableGrid = findViewById(R.id.tableGrid)
        proceedButton = findViewById(R.id.proceedButton)

        createTableCards()

        proceedButton.setOnClickListener {
            if (selectedTable != -1) {
                Toast.makeText(this, "Selected Table: $selectedTable", Toast.LENGTH_SHORT).show()
                //next page unknown
            } else {
                Toast.makeText(this, "Please select a table first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createTableCards() {
        for (i in 1..9) {
            val card = CardView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 250
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(16, 16, 16, 16)
                }
                radius = 20f
                cardElevation = 10f
                setCardBackgroundColor(Color.WHITE)
                tag = i
                isClickable = true
                isFocusable = true
            }

            val tableText = TextView(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                text = "Table $i"
                gravity = Gravity.CENTER
                textSize = 18f
                setTextColor(Color.BLACK)
            }

            card.addView(tableText)
            card.setOnClickListener {
                handleTableSelection(card, i)
            }

            tableGrid.addView(card)
        }
    }

    private fun handleTableSelection(selectedCard: CardView, tableNumber: Int) {
        for (i in 0 until tableGrid.childCount) {
            val card = tableGrid.getChildAt(i) as CardView
            card.setCardBackgroundColor(Color.WHITE)
        }
        selectedCard.setCardBackgroundColor(Color.parseColor("#D84040"))
        selectedTable = tableNumber
    }
}