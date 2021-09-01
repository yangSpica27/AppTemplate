package com.spica.app.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {


  init {
    Timber.d("初始化：MainViewModel")
  }

}