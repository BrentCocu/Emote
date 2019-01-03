package com.example.brentcocu.emote.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.databinding.ActivityMainBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.ui.emotions.EmotionEditFragment
import com.example.brentcocu.emote.ui.emotions.EmotionManagementFragment
import com.example.brentcocu.emote.viewmodels.EmotionManagementViewModel
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), ColorPickerDialogListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialog: EmotionEditFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = LayoutInflater.from(this)
        binding = ActivityMainBinding.inflate(inflater)

        setContentView(binding.root)

        binding.bar.replaceMenu(R.menu.main)
        binding.bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.overview -> {
                    toast("overview")
                    return@setOnMenuItemClickListener true
                }
                R.id.emotions -> {
                    toast("emotions")
                    return@setOnMenuItemClickListener true
                }
                R.id.settings -> {
                    toast("settings")
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }

        showEmotionManagementFragment()
    }

    private fun showEmotionManagementFragment() {
        val fragment = EmotionManagementFragment()
        replaceContent(fragment)
        val model = ViewModelProviders
            .of(this).get(EmotionManagementViewModel::class.java)
        model.selectedEmotion.observe(this,
            Observer { if (it != null) showEmotionEditFragment() }
        )
        binding.fab.setOnClickListener { model.select(Emotion()) }
    }

    private fun showEmotionEditFragment() {
        dialog = EmotionEditFragment()
        showDialog(dialog)
        dialog.onColorEdit.observe(this,
            Observer { if (it) showColorPickerDialog() }
        )
    }

    private fun showDialog(dialogFragment: DialogFragment) =
        dialogFragment.show(supportFragmentManager, dialogFragment.tag)

    private fun showColorPickerDialog() {
        dialog.dismiss()
        ColorPickerDialog.newBuilder()
            .setAllowCustom(false)
            .setColorShape(ColorShape.CIRCLE)
            .setShowColorShades(false)
            .setShowAlphaSlider(false)
            .setSelectedButtonText(R.string.select)
            .setDialogTitle(R.string.empty)
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setPresets(ColorPickerDialog.MATERIAL_COLORS)
            .show(this)
    }

    private fun replaceContent(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.contentRoot.id, fragment)
        transaction.commit()
    }

    override fun onDialogDismissed(dialogId: Int) = showDialog(dialog)

    override fun onColorSelected(dialogId: Int, color: Int) = dialog.setColor(color)
}