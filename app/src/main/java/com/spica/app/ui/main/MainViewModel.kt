package com.spica.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.spica.app.model.user.UserData
import com.spica.app.persistence.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
  userDao: UserDao
) : ViewModel() {

  private var _user: LiveData<UserData?>

  val user: LiveData<UserData?>
    get() = _user

  init {
    Timber.d("初始化：MainViewModel")
    _user = userDao.queryUserLiveDate()
  }


  /**
   * 退出账号
   */
  fun exitAccount() {

  }


}