package com.spica.app.ui.main

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationBarView
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {


  private val viewModel: MainViewModel by viewModels()

  private val onItemSelectedListener by lazy {
    NavigationBarView.OnItemSelectedListener {

      when (it.itemId) {
        R.id.home -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.HOME
          return@OnItemSelectedListener true
        }
        R.id.blog -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.BLOG
          return@OnItemSelectedListener true
        }
        R.id.search -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.SEARCH
          return@OnItemSelectedListener true
        }
        R.id.project -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.SEARCH
          return@OnItemSelectedListener true
        }
        R.id.profile -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.ME
          return@OnItemSelectedListener true
        }
      }

      return@OnItemSelectedListener false
    }
  }

  override fun initializer() {
    initView()
  }


  private fun initView() {
    viewBinding.mainViewPager.isUserInputEnabled = false
    viewBinding.mainViewPager.offscreenPageLimit = 5
    viewBinding.mainViewPager.adapter = MainPagerAdapter(this)
    viewBinding.bottomNavigationBar.setOnItemSelectedListener(onItemSelectedListener)
  }


  override fun setupViewBinding(inflater: LayoutInflater):
      ActivityMainBinding =
    ActivityMainBinding.inflate(inflater)
}