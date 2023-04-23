package com.example.foodinfo.features.search_filter.fragment

import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.databinding.FragmentSelectSearchFilterBinding
import com.example.foodinfo.features.search_filter.view_model.SelectSearchFilterViewModel


class SelectSearchFilterFragment : BaseFragment<FragmentSelectSearchFilterBinding>(
    FragmentSelectSearchFilterBinding::inflate
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