package com.spica.app.tools.keyboard

data class KeyboardVisibilityChanged(
  val visible: Boolean,
  val contentHeight: Int,
  val contentHeightBeforeResize: Int
)