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
import com.example.brentcocu.emote.viewmodels.EmotionListViewModel
import com.jaredrummler.android.colorpicker.ColorPickerDialog

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = LayoutInflater.from(this)
        binding = ActivityMainBinding.inflate(inflater)

        setContentView(binding.root)

        binding.bar.replaceMenu(R.menu.main)

        showEmotionManagementFragment()
    }

    private fun showEmotionManagementFragment() {
        val fragment = EmotionManagementFragment()
        replaceContent(fragment)
        val model = ViewModelProviders
            .of(this).get(EmotionListViewModel::class.java)
        model.selectedEmotion.observe(this,
            Observer { if (it != null) showEmotionEditFragment() }
        )
        binding.fab.setOnClickListener { model.select(Emotion()) }
    }

    private fun showEmotionEditFragment() {
        val dialog = EmotionEditFragment()
        showDialog(dialog)
        dialog.onDone.observe(this,
            Observer { if (it) dialog.dismiss() }
        )
    }

    private fun replaceContent(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.contentRoot.id, fragment)
        transaction.commit()
    }

    private fun showDialog(dialogFragment: DialogFragment) {
        val transaction = this.supportFragmentManager.beginTransaction()
        dialogFragment.show(transaction, dialogFragment.tag)
    }

    private fun showColorPickerDialog() {
        ColorPickerDialog.newBuilder()
            .setAllowCustom(false)
    }
}