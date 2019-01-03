package com.example.brentcocu.emote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.brentcocu.emote.databinding.EmotionEditFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.viewmodels.EmotionListViewModel
import org.jetbrains.anko.AnkoLogger

class EmotionEditFragment : DialogFragment(), AnkoLogger {

    private lateinit var binding: EmotionEditFragmentBinding
    private lateinit var model: EmotionListViewModel
    private lateinit var emotion: Emotion

    private val _onColorEdit = MutableLiveData<Boolean>()
    val onColorEdit: LiveData<Boolean> = _onColorEdit

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

        setupListeners()
        bind()
    }

    fun setColor(color: Int) {
        emotion.color = color
    }

    private fun bind() {
        binding.emotion = emotion
        if (isNewEmotion())
            binding.deleteBttn.visibility = View.GONE
    }

    private fun setupListeners() {
        binding.colorView.setOnClickListener { _onColorEdit.value = true }
        binding.updateBttn.setOnClickListener {
            if (isNewEmotion()) {
                if (model.add(emotion)) dismiss()
            } else
                if (model.update(emotion)) dismiss()
        }
        if (!isNewEmotion())
            binding.deleteBttn.setOnClickListener {
                model.delete(emotion)
                dismiss()
            }
    }

    private fun isNewEmotion(): Boolean {
        return emotion.id == null
    }
}

interface EmotionEditFragmentActions {
    fun update(emotion: Emotion): Boolean
    fun delete(emotion: Emotion)
    fun add(emotion: Emotion): Boolean
}