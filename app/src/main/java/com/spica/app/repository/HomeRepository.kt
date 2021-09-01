package com.spica.app.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.spica.app.model.banner.BannerData
import com.spica.app.network.WanAndroidClient
import com.spica.app.persistence.dao.BannerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class HomeRepository @Inject constructor(
  private val wanAndroidClient: WanAndroidClient,
  private val bannerDao: BannerDao
) : Repository {

  @WorkerThread
  fun fetchBanner(
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {

    var banners: List<BannerData>
    val response = wanAndroidClient.fetchBanner()
    response.suspendOnSuccess {
      banners = data.data
      bannerDao.deleteAllBanner()
      bannerDao.insertBanner(banners)
      emit(banners)
    }.onError {
      onError("${message()}")
    }.onException {
      onError(message)
    }
  }.onStart {
    onStart()
  }.onCompletion {
    onComplete()
  }.flowOn(Dispatchers.IO)

}