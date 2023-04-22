package com.example.foodinfo.core.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridItemDecoration(
    private val horizontal: Int = 0,
    private val vertical: Int = 0,
    private val columnsCount: Int = 2,
    @RecyclerView.Orientation
    private val orientation: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        when (orientation) {
            RecyclerView.VERTICAL   -> {
                val rowsCount = getRowIndex(state.itemCount)

                val currentColumn = parent.getChildAdapterPosition(view) % columnsCount
                val currentRow = getRowIndex(parent.getChildAdapterPosition(view) + 1)

                val isFirstRow = currentRow == 1
                val isLastRow = currentRow == rowsCount

                with(outRect) {
                    left = currentColumn * horizontal / columnsCount
                    right = horizontal - (currentColumn + 1) * horizontal / columnsCount
                    top = if (!isFirstRow) vertical / 2 else 0
                    bottom = if (!isLastRow) vertical / 2 else 0
                }
            }
            RecyclerView.HORIZONTAL -> {
                throw NotImplementedError()
            }
        }
    }

    private fun getRowIndex(position: Int): Int {
        return if (position % columnsCount == 0) {
            position / columnsCount
        } else {
            (position / columnsCount) + 1
        }
    }
}