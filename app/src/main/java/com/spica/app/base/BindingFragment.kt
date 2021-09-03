package com.spica.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding


abstract class BindingFragment<ViewBindingType : ViewBinding> : Fragment(), LifecycleObserver {

  private var isFirstLoad = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    allowEnterTransitionOverlap = true
    allowReturnTransitionOverlap = true
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = setupViewBinding(inflater, container)
    return requireNotNull(_binding).root
  }

  // Variables
  private var _binding: ViewBindingType? = null


  protected val viewBinding
    get() = requireNotNull(_binding)


  abstract fun setupViewBinding(
    inflater: LayoutInflater, container: ViewGroup?,
  ): ViewBindingType

  abstract fun init()


  val Int.dp: Int
    get() = (this * resources.displayMetrics.density + 0.5F).toInt()

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  private fun clearViewBinding() {
//        _binding = null
    viewLifecycleOwner.lifecycle.removeObserver(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycle.addObserver(this)
  }

  override fun onResume() {
    super.onResume()
    if (isFirstLoad) {
      isFirstLoad = false
      init()
    }
  }

  override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
    var animation = super.onCreateAnimation(transit, enter, nextAnim)
    if (animation == null && nextAnim != 0) {
      try {
        animation = AnimationUtils.loadAnimation(activity, nextAnim)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    if (animation != null) {
      view?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
      animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) = Unit
        override fun onAnimationStart(animation: Animation?) = Unit
        override fun onAnimationEnd(animation: Animation?) {
          view?.setLayerType(View.LAYER_TYPE_NONE, null)
        }
      })
    }
    return animation
  }

}