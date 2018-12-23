package com.example.brentcocu.emote.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brentcocu.emote.databinding.EmotionManagementItemFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.ui.EmotionListAdapter.EmotionViewHolder
import com.example.brentcocu.emote.viewmodels.EmotionListActions

typealias Binding = EmotionManagementItemFragmentBinding

class EmotionListAdapter(private var dataSet: List<Emotion>, private val actions: EmotionListActions) :
    RecyclerView.Adapter<EmotionViewHolder>() {

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = Binding.inflate(inflater)

        return EmotionViewHolder(binding)
    }

    // Return the size of the dataset
    override fun getItemCount(): Int = dataSet.size

    // Replace the contents of a view
    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    // Replace dataset and notify the adapter
    fun onDataSetChange(dataSet: List<Emotion>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    inner class EmotionViewHolder(private val binding: Binding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.actions = actions
        }

        fun bind(item: Emotion) {
            binding.item = item
        }
    }

}