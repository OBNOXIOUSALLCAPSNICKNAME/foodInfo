package com.example.foodinfo.ui.fragment

import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchFilterSelectBinding
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.view_model.SearchFilterSelectViewModel


class SearchFilterSelectFragment : BaseFragment<FragmentSearchFilterSelectBinding>(
    FragmentSearchFilterSelectBinding::inflate
) {

    private val viewModel: SearchFilterSelectViewModel by appViewModels()


    override fun initUI() {
        super.initUI()
        binding.hint.textView.text = getString(
            R.string.TBD_screen,
            viewModel.featureName
        )
    }
}