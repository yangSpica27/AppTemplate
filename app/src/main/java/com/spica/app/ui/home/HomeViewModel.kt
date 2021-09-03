package com.spica.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spica.app.model.banner.BannerData
import com.spica.app.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val homeRepository: HomeRepository
) : ViewModel() {

  private lateinit var _bannerFlow: StateFlow<List<BannerData?>>

  val bannerFLow: StateFlow<List<BannerData?>>
    get() = _bannerFlow

  fun getBanner(
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) {
    _bannerFlow = homeRepository
      .fetchBanner(onStart, onComplete, onError)
      .stateIn(
        scope = viewModelScope, //作用域
        started = WhileSubscribed(5000),//等待时间
        initialValue = listOf()//默认值
      )
  }

}