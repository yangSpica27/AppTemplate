package com.spica.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar

abstract class BindingActivity<ViewBindingType : ViewBinding> : AppCompatActivity(),
  LifecycleObserver {

  private var _binding: ViewBindingType? = null

  protected val viewBinding
    get() = requireNotNull(_binding)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = setupViewBinding(layoutInflater)
//    ImmersionBar
//      .with(this)
//      .statusBarDarkFont(true)
//      .init()
//    findViewById<ViewGroup>(android.R.id.content)
//      .updatePadding(top = ImmersionBar.getStatusBarHeight(this))
    setContentView(requireNotNull(_binding).root)
    lifecycle.addObserver(this)
    initializer()
  }

  abstract fun initializer()

  abstract fun setupViewBinding(inflater: LayoutInflater): ViewBindingType

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  private fun clearViewBinding() {
    _binding = null
    lifecycle.removeObserver(this)
  }


  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}