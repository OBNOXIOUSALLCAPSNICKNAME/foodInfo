package com.example.foodinfo.utils.extensions

import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.DialogDescriptionBinding
import com.example.foodinfo.repository.model.SVGModel
import com.example.foodinfo.utils.glide.GlideApp
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch


fun RecyclerView.restoreState(parcelable: Parcelable?) {
    parcelable ?: return
    layoutManager?.onRestoreInstanceState(parcelable)
}

fun RecyclerView.getState(): Parcelable? {
    return this.layoutManager?.onSaveInstanceState()
}

// AppCompatResources because on API < 24 have bad scale quality
fun View.getDrawableByName(name: String): Drawable? {
    val resourceId = context.resources.getIdentifier(
        name, "drawable", context.packageName
    )
    if (resourceId != 0) {
        return AppCompatResources.getDrawable(context, resourceId)
    }
    return null
}

fun ImageView.setFavorite(
    isFavorite: Boolean,
    trueColor: Int = R.attr.appPrimaryColor,
    falseColor: Int = R.attr.appMainBackgroundColor
) {
    if (isFavorite) {
        this.setColorFilter(this.context.getAttrColor(trueColor))
    } else {
        this.setColorFilter(this.context.getAttrColor(falseColor))
    }
}

fun View.baseAnimation() {
    isVisible = true
    alpha = 0f
    animate().alpha(1f).setDuration(100).setListener(null)
}

/*
    activity.currentFocus instead of passing view or calling view.requestFocus() because showSoftInput
    may be called before view.requestFocus() completes and will not display the keyboard, because received
    the wrong View, which was not focused.
 */
fun Fragment.showKeyboard() {
    this.requireActivity().let { activity ->
        ContextCompat.getSystemService(activity, InputMethodManager::class.java)?.showSoftInput(
            activity.currentFocus, InputMethodManager.SHOW_IMPLICIT
        )
    }
}

/*
    HIDE_NOT_ALWAYS instead of HIDE_IMPLICIT_ONLY because the last is closing only keyboard that was opened
    programmatically (e.g. Fragment.showKeyboard()) but ignores keyboard that was opened when user touched
    EditText.
 */
fun Fragment.hideKeyboard() {
    this.requireActivity().let { activity ->
        activity.currentFocus?.let { view ->
            ContextCompat.getSystemService(activity, InputMethodManager::class.java)?.hideSoftInputFromWindow(
                view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

fun Fragment.showDescriptionDialog(
    header: String,
    description: String,
    preview: SVGModel
) {
    val dialogBinding = DialogDescriptionBinding.inflate(
        LayoutInflater.from(requireContext())
    ).apply {
        GlideApp.with(requireContext())
            .load(preview)
            .error(R.drawable.ic_no_image)
            .into(ivPreview)
        tvHeader.text = header
        tvDescription.text = description
    }

    BottomSheetDialog(requireContext(), R.style.BottomSheetDialog).apply {
        setContentView(dialogBinding.root)
    }.show()
}

/**
 * Create coroutine with [Fragment.getViewLifecycleOwner] scope and launch [repeatOnLifecycle] with provided
 * [state] and [runnable]
 *
 * This extension helps to avoid boilerplate code.
 */
inline fun Fragment.repeatOn(
    state: Lifecycle.State,
    crossinline runnable: suspend () -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) {
            runnable.invoke()
        }
    }
}