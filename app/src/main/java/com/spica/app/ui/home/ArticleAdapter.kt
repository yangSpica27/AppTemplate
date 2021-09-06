package com.spica.app.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.spica.app.R
import com.spica.app.databinding.ItemArticleBinding
import com.spica.app.model.article.ArticleItem

class ArticleAdapter constructor(private val showStar: Boolean) :LoadMoreModule,
  BaseQuickAdapter<ArticleItem, BaseViewHolder>(R.layout.item_article){


  override fun convert(holder: BaseViewHolder, item: ArticleItem) {
    val binding = ItemArticleBinding.bind(holder.itemView)


    if (item.author.isBlank()) {
      binding.articleAuthor.text = item.shareUser
    } else {
      binding.articleAuthor.text = item.author
    }
  }
}