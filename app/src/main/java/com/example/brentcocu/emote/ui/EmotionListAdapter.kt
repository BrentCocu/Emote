package com.example.brentcocu.emote.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.models.Emotion

class EmotionListAdapter(private val dataSet: List<Emotion>) : RecyclerView.Adapter<EmotionListAdapter.EmotionViewHolder>() {

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.emotion_management_item_fragment, parent, false)
        // set the view's size, margins, padding and layout parameters

        return EmotionViewHolder(view)
    }

    // Return the size of the dataset
    override fun getItemCount(): Int = dataSet.size

    // Replace the contents of a view
    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.text = dataSet[position].name
        TODO("Actually retrieve color property from emotion object")
        holder.name.setTextColor(Color.RED)
    }

    class EmotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name : TextView = view.findViewById(R.id.name)
    }
}