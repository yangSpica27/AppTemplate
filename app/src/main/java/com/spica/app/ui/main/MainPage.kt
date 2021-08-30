package com.spica.app.ui.main

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.spica.app.base.CustomLayout

/**
 * 主页
 */
class MainPage(context: Context) : CustomLayout(context) {

  private val title = AppCompatTextView(context).apply {
    text = "Hello World"

    addView(this)
  }


  override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {

  }

  override fun onLayout(p0: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

  }
}