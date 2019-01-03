package com.example.brentcocu.emote.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.databinding.ActivityMainBinding
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.ui.emotions.EmotionManagementFragment
import com.example.brentcocu.emote.ui.moments.MomentOverviewFragment
import com.example.brentcocu.emote.viewmodels.EmotionManagementViewModel
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = LayoutInflater.from(this)
        binding = ActivityMainBinding.inflate(inflater)

        setContentView(binding.root)
        setupMenu()

        showEmotionManagementFragment()
    }

    private fun showEmotionManagementFragment(): Boolean {
        val fragment = EmotionManagementFragment()
        replaceContent(fragment)

        val model = EmotionManagementViewModel.getScopedInstance(this)
        handleMessages(model)

        binding.fab.setOnClickListener { model.select(Emotion()) }

        return true
    }

    private fun showMomentOverviewFragment(): Boolean {
        val fragment = MomentOverviewFragment()
        replaceContent(fragment)

        return true
    }

    private fun showSettingsFragment(): Boolean {
        toast("Settings")

        return true
    }

    private fun replaceContent(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.contentRoot.id, fragment)
        transaction.commit()
    }

    private fun setupMenu() {
        binding.bar.replaceMenu(R.menu.main)
        binding.bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.overview -> showMomentOverviewFragment()
                R.id.emotions -> showEmotionManagementFragment()
                R.id.settings -> showSettingsFragment()
                else -> false
            }
        }
    }
}