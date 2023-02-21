package com.example.foodinfo.utils

import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R


fun RecyclerView.restoreState(parcelable: Parcelable?) {
    parcelable ?: return
    layoutManager?.onRestoreInstanceState(parcelable)
}

fun RecyclerView.getState(): Parcelable? {
    return this.layoutManager?.onSaveInstanceState()
}

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

fun String.trimMultiline() = this.trimIndent().replace(System.lineSeparator(), "")