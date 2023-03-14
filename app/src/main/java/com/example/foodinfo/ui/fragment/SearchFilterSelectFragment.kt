package com.example.foodinfo.ui.fragment

import androidx.fragment.app.viewModels
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchFilterSelectBinding
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.view_model.SearchFilterSelectViewModel


class SearchFilterSelectFragment : BaseFragment<FragmentSearchFilterSelectBinding>(
    FragmentSearchFilterSelectBinding::inflate
) {

    private val viewModel: SearchFilterSelectViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }


    override fun initUI() {
        super.initUI()
        binding.hint.textView.text = getString(
            R.string.TBD_screen,
            viewModel.featureName
        )
    }
}