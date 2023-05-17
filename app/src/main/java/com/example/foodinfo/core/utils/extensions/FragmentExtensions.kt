package com.example.foodinfo.core.utils.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.foodinfo.core.utils.HintManager


/*
    activity.currentFocus instead of view because showSoftInput may be called before view.requestFocus()
    completes and will not display the keyboard, because received the wrong View, which was not focused.
 */
fun Fragment.showKeyboard(view: View) {
    this.requireActivity().let { activity ->
        view.requestFocus()
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


inline fun <reified VM : ViewModel> Fragment.appViewModels() = viewModels<VM> {
    requireActivity().appComponent.viewModelsFactory()
}

fun Fragment.appHintManager(): Lazy<HintManager> {
    return lazy { HintManager(requireContext()) }
}