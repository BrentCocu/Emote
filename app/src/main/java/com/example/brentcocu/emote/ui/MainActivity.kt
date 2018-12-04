package com.example.brentcocu.emote.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.brentcocu.emote.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = EmotionManagementFragment()
        fragmentTransaction.add(R.id.content_root, fragment)
        fragmentTransaction.commit()
    }
}
