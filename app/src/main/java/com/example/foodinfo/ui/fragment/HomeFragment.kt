package com.example.foodinfo.ui.fragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentHomeBinding
import com.example.foodinfo.ui.adapter.HomeCategoriesAdapter
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.observeState
import com.example.foodinfo.view_model.HomeViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by appViewModels()

    private lateinit var recyclerAdapter: HomeCategoriesAdapter

    private val onItemClickListener: (Int) -> Unit = { categoryID ->
        findNavController().navigate(
            HomeFragmentDirections.actionFHomeToFSearchCategory(categoryID)
        )
    }

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            HomeFragmentDirections.actionFHomeToFSearchInput()
        )
    }


    override fun initUI() {
        recyclerAdapter = HomeCategoriesAdapter(
            onItemClickListener
        )
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