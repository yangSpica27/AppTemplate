package com.spica.app.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.spica.app.model.user.UserData
import com.spica.app.network.WanAndroidClient
import com.spica.app.persistence.dao.UserDao
import com.spica.app.tools.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * 登录Repository
 */
class LoginRepository @Inject constructor(
  private val wanAndroidClient: WanAndroidClient,
  private val userDao: UserDao
) : Repository {

  private var isLogin by Preference(Preference.IS_LOGIN, false)


  /**
   * 登录
   * userName:用户名
   * password：密码
   */
  @WorkerThread
  fun login(
    userName: String,
    password: String,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    var userData: UserData
    val response = wanAndroidClient.login(userName, password)
    response.suspendOnSuccess {
      userData = data.userData
      userDao.deleteUser()
      userDao.insert(userData)
      emit(userData)
    }.onError {
      onError("${message()}")
    }
      .onException {
        onError(message)
      }
  }.onStart {
    onStart()
  }.onCompletion {
    onComplete()
  }.flowOn(Dispatchers.IO)


  /**
   * 注册
   * userName:用户名
   * password：密码
   */
  fun register(
    userName: String,
    password: String,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    var userData: UserData
    val response = wanAndroidClient.register(userName, password)
    response.suspendOnSuccess {
      userData = data.userData
      userDao.deleteUser()
      userDao.insert(userData)
      emit(userData)
    }.onError {
      onError("${message()}")
    }
      .onException {
        onError(message)
      }
  }.onStart {
    onStart()
  }.onCompletion {
    onComplete()
  }.flowOn(Dispatchers.IO)

}