package com.spica.app.ui.homecontainer

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spica.app.ui.home.HomeFragment
import com.spica.app.ui.square.SquareFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  companion object {
    const val HOME = 0 //首页
    const val SQUARE = 1  //广场
    const val UPDATE = 2 //最新咨询
    const val SYSTEM = 3 //体系
    const val NAVIGATION = 4 //导航
  }

  private val fragmentsCreators: Map<Int, () -> Fragment> = mapOf(
    HOME to { HomeFragment() },
    SQUARE to { SquareFragment() },
    UPDATE to { Fragment() },
    SYSTEM to { Fragment() },
    NAVIGATION to { Fragment() }
  )

  override fun getItemCount(): Int = fragmentsCreators.size


  override fun createFragment(position: Int): Fragment {
    return fragmentsCreators[position]?.invoke() ?: HomeContainerFragment()
  }


}