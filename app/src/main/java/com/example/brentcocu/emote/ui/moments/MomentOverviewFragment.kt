package com.example.brentcocu.emote.ui.moments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brentcocu.emote.databinding.MomentOverviewFragmentBinding

class MomentOverviewFragment: Fragment() {

    private lateinit var binding: MomentOverviewFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = MomentOverviewFragmentBinding.inflate(inflater)

        return binding.root
    }
}