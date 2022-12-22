package com.example.kotlin_stady.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlin_stady.R
import com.example.kotlin_stady.base.BaseActivity
import com.example.kotlin_stady.ui.fragment.HomeFragment

class HomeActivity : BaseActivity() {

    private val currentFragment: Fragment
    private val rootId: Int

    init {
        currentFragment = HomeFragment()
        rootId = R.id.homeFrameLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initFragmentManager()
    }

    private fun initFragmentManager() {
        supportFragmentManager.beginTransaction()
            .replace(rootId, currentFragment)
            .commit()
    }


}