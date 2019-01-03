package com.example.brentcocu.emote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brentcocu.emote.databinding.EmotionManagementFragmentBinding
import com.example.brentcocu.emote.viewmodels.EmotionListViewModel

class EmotionManagementFragment : Fragment() {

    private lateinit var binding: EmotionManagementFragmentBinding
    private lateinit var model: EmotionListViewModel
    private lateinit var adapter: EmotionListAdapter

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
                .of(it).get(EmotionListViewModel::class.java)
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
                Observer { res ->
                    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}