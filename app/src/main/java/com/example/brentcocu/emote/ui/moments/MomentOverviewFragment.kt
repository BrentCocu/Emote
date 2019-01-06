package com.example.brentcocu.emote.ui.moments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.databinding.MomentOverviewFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.viewmodels.MomentOverviewViewModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class MomentOverviewFragment : Fragment(), AnkoLogger, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: MomentOverviewFragmentBinding
    private lateinit var model: MomentOverviewViewModel
    private lateinit var adapter: MomentListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = MomentOverviewFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view-model and adapter
        model = MomentOverviewViewModel.getScopedInstance(requireActivity())
        adapter = MomentListAdapter(model)
        adapter.setHasStableIds(true)

        // Hook recyclerView and initialize
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MomentOverviewFragment.context)
            adapter = this@MomentOverviewFragment.adapter
        }

        setupCallbacks()
        setupListeners()
    }

    private fun setupCallbacks() {
        model.moments.observe(this,
            Observer { list -> adapter.onDataSetChange(list) }
        )
    }

    private fun setupListeners() {
        binding.query.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    model.filterByContent(s.toString())
                    info("Query is: $s")
                }
            }
        })
        binding.clearBttn.setOnClickListener { binding.query.clearComposingText() }
        binding.addBttn.setOnClickListener { info("AddButton was clicked") }
        binding.filterBttn.setOnClickListener { showPopup(it) }
    }

    private fun showPopup(anchor: View) {
        PopupMenu(requireContext(), anchor).apply {
            setOnMenuItemClickListener(this@MomentOverviewFragment)
            inflate(R.menu.moment_filter)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_emotion -> {
                info("Filter by emotion clicked")
                true
            }
            R.id.filter_date -> {
                info("Filter by date clicked")
                true
            }
            else -> false
        }
    }
}

interface MomentOverviewFragmentActions {
    fun filterByContent(query: String)
    fun filterByEmotion(emotion: Emotion)
    fun filterByDate(from: Date, to: Date)
}