package com.spica.app.repository



interface Repository {

  // 用于记录网络请求的状态
  var isLoading: Boolean
}