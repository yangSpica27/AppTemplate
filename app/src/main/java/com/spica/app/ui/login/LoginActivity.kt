package com.spica.app.ui.login

import android.graphics.Color
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityLoginBinding
import com.spica.app.tools.keyboard.FluidContentResizer


/**
 * 登录Activity
 */
class LoginActivity : BindingActivity<ActivityLoginBinding>() {

  private val enterTransform by lazy {
    MaterialContainerTransform().apply {
      startView = viewBinding.panelLogin
      endView = viewBinding.panelRegister
      addTarget(endView)
      pathMotion = MaterialArcMotion()
      scrimColor = Color.TRANSPARENT
    }
  }

  private val exitTransform by lazy {
    MaterialContainerTransform().apply {
      startView = viewBinding.panelRegister
      endView = viewBinding.panelLogin
      addTarget(endView)
      pathMotion = MaterialArcMotion()
      scrimColor = Color.TRANSPARENT
    }

  }

  /**
   * 进入注册页面的动画
   */
  private fun enterRegister() {
    TransitionManager.beginDelayedTransition(
      findViewById(android.R.id.content),
      enterTransform
    )
    viewBinding.panelLogin.visibility = View.GONE
    viewBinding.panelRegister.visibility = View.VISIBLE
  }

  /**
   * 退出注册页面的动画
   */
  private fun exitRegister() {
    TransitionManager.beginDelayedTransition(
      findViewById(android.R.id.content),
      exitTransform
    )
    viewBinding.panelRegister.visibility = View.GONE
    viewBinding.panelLogin.visibility = View.VISIBLE
  }


  override fun initializer() {
    FluidContentResizer.listen(this)
    viewBinding.btnLogin.setOnClickListener { exitRegister() }
    viewBinding.btnRegister.setOnClickListener { enterRegister() }
  }

  override fun setupViewBinding(inflater: LayoutInflater):
      ActivityLoginBinding =
    ActivityLoginBinding.inflate(inflater)

}