package com.example.foodinfo.features.home.fragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentHomeBinding
import com.example.foodinfo.features.home.adapter.categoryAdapterDelegate
import com.example.foodinfo.features.home.model.CategoryVHModel
import com.example.foodinfo.features.home.view_model.HomeViewModel
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.base.adapter.AppListAdapter
import com.example.foodinfo.ui.base.adapter.appListAdapter
import com.example.foodinfo.ui.ListItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.observeState


class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val onItemClickListener: (CategoryVHModel) -> Unit = { category ->
        findNavController().navigate(
            HomeFragmentDirections.actionFHomeToFSearchCategory(category.ID, category.name)
        )
    }

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            HomeFragmentDirections.actionFHomeToFSearchInput()
        )
    }


    private val viewModel: HomeViewModel by appViewModels()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        categoryAdapterDelegate(onItemClickListener)
    )


    override fun initUI() {
        binding.ivSearch.setOnClickListener { onSearchClickListener() }

        with(binding.rvCategories) {
            layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = recyclerAdapter
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.home_categories_space),
                    RecyclerView.HORIZONTAL
                )
            )
            itemAnimator = null
        }

        binding.hintTop.textView.text = getString(
            R.string.TBD_screen,
            resources.getString(R.string.top_recipes_text)
        )
        binding.hintTrending.textView.text = getString(
            R.string.TBD_screen,
            resources.getString(R.string.trending_text)
        )
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.categories,
            useLoadingData = true,
            onSuccess = recyclerAdapter::submitList,
        )
    }
}