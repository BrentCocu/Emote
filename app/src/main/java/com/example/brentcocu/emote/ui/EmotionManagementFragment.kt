package com.example.brentcocu.emote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.models.Emotion
import com.example.brentcocu.emote.viewmodels.EmotionListViewModel

class EmotionManagementFragment : Fragment() {

    private lateinit var emotionListViewModel: EmotionListViewModel
    private lateinit var emotionListAdapter: EmotionListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.emotion_management_fragment, container, false)

        // Initialize view model and adapter
        emotionListViewModel = ViewModelProviders
            .of(this)
            .get(EmotionListViewModel::class.java)
        emotionListAdapter = EmotionListAdapter(emotionListViewModel.dataSet.value!!)

        val viewManager = LinearLayoutManager(this.context)

        // Hook recyclerView and initialize
        view.findViewById<RecyclerView>(R.id.container).apply {
            layoutManager = viewManager
            adapter = emotionListAdapter
        }

        setupCallbacks()

        return view
    }

    private fun setupCallbacks() {
        emotionListViewModel.dataSet.observe(
            this,
            Observer<List<Emotion>> {
                newData -> emotionListAdapter.setDataSet(newData)
            }
        )
    }

}