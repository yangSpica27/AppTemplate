package com.spica.app.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.RoundedCornersTransformation
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.FragmentHomeBinding
import com.spica.app.model.banner.BannerData
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 主页
 */
@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>() {


  private val viewModel: HomeViewModel by viewModels()


  private val banners = mutableListOf<BannerData?>()


  private val adpter by lazy {
    ArticleAdapter(false)
  }

  private val bannerAdapter = object : BannerImageAdapter<BannerData>(banners) {


    override fun onBindView(holder: BannerImageHolder, data: BannerData, position: Int, size: Int) {
      holder.imageView.load(data.imagePath) {
        transformations(RoundedCornersTransformation(8.dp.toFloat()))
      }
    }
  }

  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      FragmentHomeBinding = FragmentHomeBinding.inflate(inflater)

  @SuppressLint("NotifyDataSetChanged")
  override fun init() {
    adpter.loadMoreModule.setOnLoadMoreListener {

    }
    viewBinding.recyclerView.adapter = adpter
    viewBinding.banner
      .addBannerLifecycleObserver(viewLifecycleOwner)
      .indicator = CircleIndicator(requireContext())

    viewBinding.banner.setAdapter(bannerAdapter)

    viewModel.getBanner(
      onStart = {

      },
      onComplete = {

      },
      onError = {
        it?.let {
          showToast(it)
        }
      })
    lifecycleScope.launch {
      viewModel.bannerFLow.collect {
        lifecycleScope.launch(Dispatchers.Main) {
          banners.clear()
          banners.addAll(it)
          bannerAdapter.notifyDataSetChanged()
        }
      }
    }
  }


  private fun showToast(message: String) {
    lifecycleScope.launch(Dispatchers.Main){
      Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
  }

  private fun loadData(isUpdate: Boolean){

  }


}