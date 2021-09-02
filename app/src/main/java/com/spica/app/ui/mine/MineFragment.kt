package com.spica.app.ui.mine

import android.view.LayoutInflater
import android.view.ViewGroup
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.FragmentMineBinding
import com.spica.app.tools.Preference


/**
 * 我的
 */
class MineFragment : BindingFragment<FragmentMineBinding>() {

  private var isLogin by Preference(Preference.IS_LOGIN, false)

  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      FragmentMineBinding = FragmentMineBinding.inflate(inflater, container, false)

  override fun init() {

  }


  override fun onResume() {
    super.onResume()
    if (isLogin) {

    }
  }
}