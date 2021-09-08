package com.spica.app.ui.userinfo

import android.view.LayoutInflater
import com.google.android.material.appbar.AppBarLayout
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityUserInfoBinding

/**
 * 用户信息展示页
 */
private const val IMG_URL = "https://unsplash.it/400/800/?random"

class UserInfoActivity : BindingActivity<ActivityUserInfoBinding>() {


  override fun initializer() {
    viewBinding.appBarLayout.addOnOffsetChangedListener(
      AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
        if (verticalOffset <= viewBinding.headLayout.height / 2) {
          viewBinding.collapsingToolbarLayout.title = "涩郎"
        } else {
          viewBinding.collapsingToolbarLayout.title = " "
        }
      })
  }

  override fun setupViewBinding(inflater: LayoutInflater): ActivityUserInfoBinding =
    ActivityUserInfoBinding.inflate(layoutInflater)


}