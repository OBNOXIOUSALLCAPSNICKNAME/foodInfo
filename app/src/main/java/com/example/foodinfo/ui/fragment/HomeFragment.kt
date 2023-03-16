package com.example.foodinfo.ui.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentHomeBinding
import com.example.foodinfo.ui.adapter.HomeCategoriesAdapter
import com.example.foodinfo.ui.base.DataObserverFragment
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.view_model.HomeViewModel


class HomeFragment : DataObserverFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

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
        observeData(
            dataFlow = viewModel.categories,
            useLoadingData = true,
            onInitUI = recyclerAdapter::submitList,
            onRefreshUI = recyclerAdapter::submitList
        )
    }
}