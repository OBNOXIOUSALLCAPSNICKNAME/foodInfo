package com.example.foodinfo.core.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ListItemDecoration(
    private val space: Int,
    @RecyclerView.Orientation
    private val orientation: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) != state.itemCount - 1) {
            when (orientation) {
                RecyclerView.HORIZONTAL -> outRect.right = space
                RecyclerView.VERTICAL   -> outRect.bottom = space
            }
        }
    }
}