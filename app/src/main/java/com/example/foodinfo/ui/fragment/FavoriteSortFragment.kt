package com.example.foodinfo.ui.fragment

import androidx.navigation.fragment.findNavController
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentFavoriteSortBinding
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.view_model.FavoriteSortViewModel


class FavoriteSortFragment : BaseFragment<FragmentFavoriteSortBinding>(
    FragmentFavoriteSortBinding::inflate
) {

    private val viewModel: FavoriteSortViewModel by appViewModels()

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