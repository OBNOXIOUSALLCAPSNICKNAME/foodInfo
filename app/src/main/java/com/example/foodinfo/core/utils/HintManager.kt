package com.example.foodinfo.core.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.foodinfo.R
import com.example.foodinfo.core.utils.glide.GlideApp
import com.example.foodinfo.core.utils.glide.svg.SVGModel
import com.example.foodinfo.databinding.DialogDescriptionBinding
import com.example.foodinfo.domain.model.LabelHint
import com.example.foodinfo.domain.model.NutrientHint
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HintManager(private val context: Context) {

    private val binding: DialogDescriptionBinding by lazy {
        DialogDescriptionBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    private val dialog: BottomSheetDialog by lazy {
        BottomSheetDialog(context, R.style.BottomSheetDialog).apply {
            setContentView(binding.root)
            setOnCancelListener { isActive = false }
            setOnShowListener { isActive = true }
        }
    }

    private var isActive = false


    fun show(
        content: String,
        preview: SVGModel? = null,
        header: String? = null
    ) {
        if (isActive) return

        binding.apply {
            if (preview != null) {
                GlideApp.with(context)
                    .load(preview)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivPreview)
                ivPreview.isVisible = true
            } else {
                ivPreview.isVisible = false
            }
            if (header != null) {
                tvHeader.text = header
                tvHeader.isVisible = true
            } else {
                tvHeader.isVisible = false
            }
            tvDescription.text = content
        }

        dialog.show()
    }

    inline fun showLabel(fragment: Fragment, crossinline hintProvider: suspend () -> LabelHint) {
        fragment.viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            hintProvider().apply {
                withContext(Dispatchers.Main) {
                    show(description, preview, name)
                }
            }
        }
    }

    inline fun showNutrient(fragment: Fragment, crossinline hintProvider: suspend () -> NutrientHint) {
        fragment.viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            hintProvider().apply {
                withContext(Dispatchers.Main) {
                    show(description, preview, name)
                }
            }
        }
    }
}