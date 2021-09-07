package com.spica.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spica.app.model.article.ArticleData
import com.spica.app.model.article.ArticleItem
import com.spica.app.model.banner.BannerData
import com.spica.app.repository.HomeRepository
import com.spica.app.repository.SquareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
  private val homeRepository: HomeRepository,
  private val squareRepository: SquareRepository
) : ViewModel() {


  private lateinit var _bannerFlow: StateFlow<List<BannerData?>>

  //是否正在加载
  private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

  //错误消息
  val errorMessage: MutableStateFlow<String?> = MutableStateFlow("")

  //主页的页码
  private val homeCurrentPage: MutableStateFlow<Int> = MutableStateFlow(0)

  //广场的页码
  private val squareCurrentPage: MutableStateFlow<Int> = MutableStateFlow(0)

  private val squareList: MutableStateFlow<ArrayList<ArticleItem>> = MutableStateFlow(arrayListOf())

  private val _articleFlow: Flow<ArticleData> = homeCurrentPage.flatMapConcat { page ->
    homeRepository.fetchHomeArticle(
      onStart = {
        isLoading.value = true
      },
      onError = {
        errorMessage.value = it
        isLoading.value = false
      },
      onComplete = {
        isLoading.value = false
      },
      page = page
    )
  }

  private val _articleSquareFLow: Flow<ArticleData> = squareCurrentPage.flatMapConcat { page ->
    squareRepository.getSquareArticle(
      page,
      onError = {
        errorMessage.value = it
      })
  }

  val squareArticleFlow: Flow<ArticleData>
    get() = _articleSquareFLow


  val bannerFLow: StateFlow<List<BannerData?>>
    get() = _bannerFlow

  val articleFLow: Flow<ArticleData>
    get() = _articleFlow

  /**
   * 获取Banner
   */
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

  /**
   * 加载主页的文章
   */
  fun loadMoreHomeArticle(isRefresh: Boolean) {
    if (isRefresh) {
      homeCurrentPage.value = 0
    } else {
      homeCurrentPage.value++
    }
  }

  /**
   * 加载广场的文章
   */
  fun loadMoreSquareArticle(isRefresh: Boolean) {
    if (isRefresh) {
      //清空数据
      squareList.value = arrayListOf()
      squareCurrentPage.value = 0
    } else {
      squareCurrentPage.value++
    }
  }


}