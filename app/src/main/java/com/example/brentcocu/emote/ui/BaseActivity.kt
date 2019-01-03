package com.example.brentcocu.emote.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.brentcocu.emote.viewmodels.BaseViewModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    protected fun handleMessages(viewModel: BaseViewModel) {
        viewModel.onMessage.observe(this, Observer {
            it.getContentIfNotHandled()?.let { resId -> toast(resId) }
        })
    }
}