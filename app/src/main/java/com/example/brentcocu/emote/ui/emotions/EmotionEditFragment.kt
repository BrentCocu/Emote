package com.example.brentcocu.emote.ui.emotions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.databinding.EmotionEditFragmentBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.viewmodels.EmotionManagementViewModel
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape

class EmotionEditFragment : DialogFragment(), ColorPickerDialogListener {

    private lateinit var binding: EmotionEditFragmentBinding
    private lateinit var model: EmotionManagementViewModel
    private lateinit var emotion: Emotion

    private val _onShowRequest = MutableLiveData<Boolean>()
    val onShowRequest: LiveData<Boolean> = _onShowRequest

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        binding = EmotionEditFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            model = ViewModelProviders
                .of(it).get(EmotionManagementViewModel::class.java)
        }

        emotion = model.selectedEmotion.value!!

        setupListeners()
        bind()
    }

    private fun bind() {
        binding.emotion = emotion
        if (isNewEmotion())
            binding.deleteBttn.visibility = View.GONE
    }

    private fun setupListeners() {
        binding.colorView.setOnClickListener { showColorPickerDialog() }
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

    private fun showColorPickerDialog() {
        ColorPickerDialog.newBuilder()
            .setAllowCustom(false)
            .setColorShape(ColorShape.CIRCLE)
            .setShowColorShades(false)
            .setShowAlphaSlider(false)
            .setSelectedButtonText(R.string.select)
            .setDialogTitle(R.string.empty)
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setPresets(ColorPickerDialog.MATERIAL_COLORS)
            .create().also {
                it.setColorPickerDialogListener(this)
                this.dismiss()
                it.show(requireFragmentManager(), tag)
            }
    }

    override fun onDialogDismissed(dialogId: Int) {
        _onShowRequest.value = true
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        emotion.color = color
    }
}

interface EmotionEditFragmentActions {
    fun update(emotion: Emotion): Boolean
    fun delete(emotion: Emotion)
    fun add(emotion: Emotion): Boolean
}