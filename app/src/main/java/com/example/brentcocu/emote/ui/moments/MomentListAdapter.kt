package com.example.brentcocu.emote.ui.moments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brentcocu.emote.databinding.MomentOverviewItemFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.datamodels.Moment

class MomentListAdapter(private val actions: MomentListAdapterActions) :
    RecyclerView.Adapter<MomentListAdapter.MomentViewHolder>() {

    private var momentList: List<Moment> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MomentOverviewItemFragmentBinding.inflate(inflater, parent, false)

        return MomentViewHolder(binding)
    }

    override fun getItemCount(): Int = momentList.size

    override fun getItemId(position: Int): Long = momentList[position].id!!.toLong()

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        holder.bind(momentList[position])
    }

    fun onDataSetChange(momentList: List<Moment>) {
        this.momentList = momentList
        notifyDataSetChanged()
    }

    inner class MomentViewHolder(private val binding: MomentOverviewItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moment: Moment) {
            binding.emotion = actions.getEmotionById(moment.emotionId)
            binding.moment = moment
        }
    }
}

interface MomentListAdapterActions {
    fun getEmotionById(id: Int): Emotion?
}