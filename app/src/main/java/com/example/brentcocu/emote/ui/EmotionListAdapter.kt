package com.example.brentcocu.emote.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brentcocu.emote.databinding.EmotionManagementItemFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.ui.EmotionListAdapter.EmotionViewHolder

class EmotionListAdapter(private val actions: EmotionListAdapterActions) :
    RecyclerView.Adapter<EmotionViewHolder>() {

    private var emotionList: List<Emotion> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EmotionManagementItemFragmentBinding.inflate(inflater)

        return EmotionViewHolder(binding)
    }

    override fun getItemCount(): Int = emotionList.size

    override fun getItemId(position: Int): Long = emotionList[position].id!!.toLong()

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        holder.bind(emotionList[position])
    }

    fun onDataSetChange(emotionList: List<Emotion>) {
        this.emotionList = emotionList
        notifyDataSetChanged()
    }

    inner class EmotionViewHolder(private val binding: EmotionManagementItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(emotion: Emotion) {
            binding.emotion = emotion
            binding.emotionView.setOnClickListener { actions.select(emotion) }
        }
    }
}

interface EmotionListAdapterActions {
    fun select(emotion: Emotion)
}