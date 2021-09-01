package com.spica.app.network

import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.banner.Banner
import com.spica.app.model.user.User
import com.spica.app.network.service.ApiService
import javax.inject.Inject


class WanAndroidClient
@Inject constructor(
  private val apiService: ApiService
) {

  suspend fun fetchBanner():
      ApiResponse<Banner> = apiService.getBanner()

  suspend fun login(userName: String, password: String):
      ApiResponse<User> = apiService.login(userName, password)

  suspend fun register(userName: String, password: String):
      ApiResponse<User> = apiService.register(userName, password, password)


}