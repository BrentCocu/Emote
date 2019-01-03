package com.example.brentcocu.emote.ui.emotions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brentcocu.emote.databinding.EmotionManagementFragmentBinding
import com.example.brentcocu.emote.viewmodels.EmotionManagementViewModel
import org.jetbrains.anko.toast

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
        activity?.let {
            model = ViewModelProviders
                .of(it).get(EmotionManagementViewModel::class.java)
        }
        adapter = EmotionListAdapter(model)
        adapter.setHasStableIds(true)

        // Hook recyclerView and initialize
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@EmotionManagementFragment.context)
            adapter = this@EmotionManagementFragment.adapter
        }

        setupCallbacks()
    }

    private fun setupCallbacks() {
        model.let {
            it.emotionList.observe(this,
                Observer { list -> adapter.onDataSetChange(list) }
            )
            it.onMessage.observe(this,
                Observer { res -> requireContext().toast(res) }
            )
            it.selectedEmotion.observe(this,
                Observer { emotion -> if (emotion != null) showEmotionEditDialog() }
            )
        }
        dialog.onShowRequest.observe(this, Observer { if (it) showEmotionEditDialog() })
    }

    private fun showEmotionEditDialog() = dialog.show(requireFragmentManager(), dialog.tag)
}