package com.example.foodinfo.features.favorite.fragment

import androidx.navigation.fragment.findNavController
import com.example.foodinfo.R
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.databinding.FragmentSortFavoriteRecipesBinding
import com.example.foodinfo.features.favorite.view_model.SortFavoriteRecipesViewModel


class SortFavoriteRecipesFragment :
    com.example.foodinfo.core.ui.base.BaseFragment<FragmentSortFavoriteRecipesBinding>(
        FragmentSortFavoriteRecipesBinding::inflate
    ) {

    private val viewModel: SortFavoriteRecipesViewModel by appViewModels()

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }


    override fun initUI() {
        binding.btnBack.setOnClickListener { onBackClickListener() }

        binding.hint.textView.text = getString(
            R.string.TBD_screen,
            viewModel.featureName
        )
    }
}