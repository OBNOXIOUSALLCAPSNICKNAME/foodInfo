package com.example.foodinfo.features.search_filter.fragment

import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchFilterSelectBinding
import com.example.foodinfo.features.search_filter.view_model.SelectSearchFilterViewModel
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.utils.extensions.appViewModels


class SelectSearchFilterFragment : BaseFragment<FragmentSearchFilterSelectBinding>(
    FragmentSearchFilterSelectBinding::inflate
) {

    private val viewModel: SelectSearchFilterViewModel by appViewModels()


    override fun initUI() {
        super.initUI()
        binding.hint.textView.text = getString(
            R.string.TBD_screen,
            viewModel.featureName
        )
    }
}