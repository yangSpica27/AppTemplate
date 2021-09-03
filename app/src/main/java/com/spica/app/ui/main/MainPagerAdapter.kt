package com.spica.app.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spica.app.ui.homecontainer.HomeContainerFragment
import com.spica.app.ui.mine.MineFragment

/**
 * 主页的pager适配器
 */
class MainPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {


  private val fragmentsCreators: Map<Int, () -> Fragment> = mapOf(
    HOME to { HomeContainerFragment() },
    BLOG to { HomeContainerFragment() },
    SEARCH to { HomeContainerFragment() },
    PROJECT to { HomeContainerFragment() },
    ME to { MineFragment() }
  )

  override fun getItemCount(): Int = fragmentsCreators.size


  override fun createFragment(position: Int): Fragment {
    return fragmentsCreators[position]?.invoke() ?: HomeContainerFragment()
  }


  companion object {
    const val HOME = 0 //首页
    const val BLOG = 1  //公众号
    const val SEARCH = 2 //搜索
    const val PROJECT = 3 //项目集
    const val ME = 4 //我的
  }
}