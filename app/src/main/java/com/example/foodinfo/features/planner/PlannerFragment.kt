package com.example.foodinfo.features.planner

import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.databinding.FragmentPlannerBinding


class PlannerFragment : BaseFragment<FragmentPlannerBinding>(
    FragmentPlannerBinding::inflate
) {

    private val viewModel: PlannerViewModel by appViewModels()


    override fun initUI() {
        super.initUI()
        binding.hint.textView.text = getString(
            R.string.TBD_screen,
            viewModel.featureName
        )
    }
}