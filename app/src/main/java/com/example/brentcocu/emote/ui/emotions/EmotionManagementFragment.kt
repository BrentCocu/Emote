package com.example.brentcocu.emote.ui.emotions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brentcocu.emote.databinding.EmotionManagementFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.viewmodels.EmotionManagementViewModel

class EmotionManagementFragment : Fragment() {

    private lateinit var binding: EmotionManagementFragmentBinding
    private lateinit var model: EmotionManagementViewModel
    private lateinit var adapter: EmotionListAdapter
    private val dialog = EmotionEditFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = EmotionManagementFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view-model and adapter
        model = EmotionManagementViewModel.getScopedInstance(requireActivity())
        adapter = EmotionListAdapter(model)
        adapter.setHasStableIds(true)

        // Hook recyclerView and initialize
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@EmotionManagementFragment.context)
            adapter = this@EmotionManagementFragment.adapter
        }

        binding.addBttn.setOnClickListener { model.select(Emotion()) }

        setupCallbacks()
    }

    private fun setupCallbacks() {
        model.emotionList.observe(this,
            Observer { list -> adapter.onDataSetChange(list) }
        )
        model.selectedEmotion.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { showEmotionEditDialog() }
        })
        dialog.onShowRequest.observe(this, Observer { if (it) showEmotionEditDialog() })
    }

    private fun showEmotionEditDialog() = dialog.show(requireFragmentManager(), dialog.tag)
}