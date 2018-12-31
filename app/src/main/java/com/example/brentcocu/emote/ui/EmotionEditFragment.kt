package com.example.brentcocu.emote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.brentcocu.emote.databinding.EmotionEditFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.viewmodels.EmotionListViewModel

class EmotionEditFragment : DialogFragment() {

    private lateinit var binding: EmotionEditFragmentBinding
    private lateinit var model: EmotionListViewModel
    private lateinit var emotion: Emotion

    private val _onDone = MutableLiveData<Boolean>()
    val onDone: LiveData<Boolean> = _onDone

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = EmotionEditFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            model = ViewModelProviders
                .of(it).get(EmotionListViewModel::class.java)
        }

        emotion = model.selectedEmotion.value!!

        setupCallbacks()
        setupListeners()
        bind()
    }

    private fun bind() {
        binding.emotion = emotion
        if (isNewEmotion())
            binding.deleteBttn.visibility = View.GONE
    }

    private fun setupListeners() {
        binding.updateBttn.setOnClickListener {
            if (isNewEmotion())
                model.add(emotion)
            else
                model.update(emotion)
            _onDone.value = true
        }
        if (!isNewEmotion())
            binding.deleteBttn.setOnClickListener {
                model.delete(emotion)
                _onDone.value = true
            }
    }

    private fun setupCallbacks() {
        model.let {
            it.onMessage.observe(this,
                Observer { res -> Toast.makeText(context, res, Toast.LENGTH_SHORT).show() }
            )
        }
    }

    private fun isNewEmotion(): Boolean {
        return emotion.id == null
    }
}

interface EmotionEditFragmentActions {
    fun update(emotion: Emotion)
    fun delete(emotion: Emotion)
    fun add(emotion: Emotion)
}