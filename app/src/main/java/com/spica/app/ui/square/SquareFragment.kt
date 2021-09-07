package com.spica.app.ui.square

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.spica.app.R
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.LayoutListBinding
import com.spica.app.model.article.ArticleItem
import com.spica.app.ui.home.ArticleAdapter
import com.spica.app.ui.home.ArticleViewModel
import com.spica.app.ui.webview.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 广场Square
 */
@AndroidEntryPoint
class SquareFragment : BindingFragment<LayoutListBinding>() {

  /**
   * viewModel
   */
  private val viewModel: ArticleViewModel by activityViewModels()

  /**
   * 列表适配器
   */
  private val listAdapter by lazy {
    ArticleAdapter(false)
  }

  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      LayoutListBinding =
    LayoutListBinding.inflate(layoutInflater, container, false)

  override fun init() {
    addDivider()
    // 触发加载更多
    listAdapter.loadMoreModule.isEnableLoadMore = true
    listAdapter.loadMoreModule.setOnLoadMoreListener {
      loadData(false)
    }
    listAdapter.setDiffCallback(DiffArticleCallBack())
    viewBinding.recyclerView.adapter = listAdapter
    addObserver()
  }

  private fun addObserver() {

    listAdapter.setOnItemClickListener { _, _, position ->
      kotlin.run {
        val intent = WebActivity.newIntent(
          requireContext(),
          listAdapter.data[position].title,
          listAdapter.data[position].link
        )
        startActivity(intent)
      }
    }

    lifecycleScope.launch {
      viewModel.squareArticleFlow.collect {
        if (viewBinding.layoutSwipe.isRefreshing) {
          viewBinding.layoutSwipe.isRefreshing = false
        }
        listAdapter.addData(it.datas)
        listAdapter.loadMoreModule.loadMoreComplete()
      }
    }
  }


  private fun loadData(isRefresh: Boolean = false) {
    viewModel.loadMoreSquareArticle(isRefresh)
  }

  /**
   * 架线
   */
  private fun addDivider() {
    //recyclerview划线
    requireContext().dividerBuilder()
      .colorRes(R.color.line_divider)
      .size(1, TypedValue.COMPLEX_UNIT_DIP)
      .build()
      .addTo(viewBinding.recyclerView)
  }


  class DiffArticleCallBack : DiffUtil.ItemCallback<ArticleItem>() {
    override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
      return oldItem.publishTime == newItem.publishTime
    }

  }


}