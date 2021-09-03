package com.spica.app.network.service

import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.article.Article
import com.spica.app.model.banner.Banner
import com.spica.app.model.user.User
import retrofit2.http.*

@Suppress("unused")
interface ApiService {

  companion object {
    const val BASE_URL = "https://www.wanandroid.com"
  }

  /**
   * 获取首页Banner
   */
  @GET("/banner/json")
  suspend fun getBanner(): ApiResponse<Banner>


  /**
   * 登录
   */
  @FormUrlEncoded
  @POST("/user/login")
  suspend fun login(
    @Field("username") userName: String,
    @Field("password") passWord: String
  ): ApiResponse<User>


  /**
   * 注册
   */
  @FormUrlEncoded
  @POST("/user/register")
  suspend fun register(
    @Field("username") userName: String,
    @Field("password") passWord: String,
    @Field("repassword") rePassWord: String
  ): ApiResponse<User>


  /**
   * 登出
   */
  @GET("/user/logout/json")
  suspend fun logOut(): ApiResponse<Any>


  /**
   * 获取首页文章列表
   */
  @GET("/article/list/{page}/json")
  suspend fun getHomeArticles(@Path("page") page: Int): ApiResponse<Article>


}