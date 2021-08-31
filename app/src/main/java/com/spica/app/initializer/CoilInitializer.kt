@file:Suppress("unused")

package com.spica.app.initializer

import android.content.Context
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy

/**
 * 初始化图片加载
 */
class CoilInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    val imageLoader = ImageLoader.Builder(context)
      .crossfade(true)
      .allowRgb565(true)
      .memoryCachePolicy(CachePolicy.DISABLED)
      .diskCachePolicy(CachePolicy.ENABLED)
      .allowHardware(false)
      .bitmapPoolingEnabled(false)
      .build()
    Coil.setImageLoader(imageLoader)
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}