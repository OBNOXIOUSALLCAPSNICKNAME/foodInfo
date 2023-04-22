package com.example.foodinfo.core.utils.extensions

import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.core.utils.SuspendClickListener


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
    falseColor: Int = R.color.main_background
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

/**
 * Shorthand for `view.setOnClickListener(SuspendClickListener(listener))`.
 *
 * @see SuspendClickListener
 */
fun View.setSuspendClickListener(listener: (() -> Unit) -> Unit) {
    this.setOnClickListener(SuspendClickListener(listener))
}