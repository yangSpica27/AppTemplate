package com.spica.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>() {

  private val viewModel: HomeViewModel by viewModels()

  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

  override fun init() {

  }
}