package com.example.foodinfo.features.favorite.fragment

import androidx.navigation.fragment.findNavController
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentFavoriteSortBinding
import com.example.foodinfo.features.favorite.view_model.SortFavoriteRecipesViewModel
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.utils.extensions.appViewModels


class SortFavoriteRecipesFragment : BaseFragment<FragmentFavoriteSortBinding>(
    FragmentFavoriteSortBinding::inflate
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