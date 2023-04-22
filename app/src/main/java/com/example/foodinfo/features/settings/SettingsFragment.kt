package com.example.foodinfo.features.settings

import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSettingsBinding
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.utils.extensions.appViewModels


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    private val viewModel: SettingsViewModel by appViewModels()


    override fun initUI() {
        super.initUI()
        binding.hint.textView.text = getString(
            R.string.TBD_screen,
            viewModel.featureName
        )
    }
}