package com.nycschool.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nycschool.SPLASH_DELAY
import com.nycschool.databinding.SplashLayoutBinding
import com.nycschool.ui.base.BaseActivity
import com.nycschool.ui.component.schoollist.SchoolListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: SplashLayoutBinding

    override fun initViewBinding() {
        binding = SplashLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {
        //no-op
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SchoolListActivity::class.java))
            finish()
        }, SPLASH_DELAY.toLong())
    }
}
