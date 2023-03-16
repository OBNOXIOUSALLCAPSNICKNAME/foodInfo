package com.example.foodinfo.utils.glide.svg

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.PictureDrawable
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.caverock.androidsvg.SVG


class SVGDrawableTranscoder : ResourceTranscoder<SVG, BitmapDrawable> {
    override fun transcode(
        toTranscode: Resource<SVG>, options: Options
    ): Resource<BitmapDrawable> {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()
        val pictureDrawable = PictureDrawable(picture)
        val bitmapDrawable = BitmapDrawable(Resources.getSystem(), pictureDrawable.toBitmap())
        return SimpleResource(bitmapDrawable)
    }
}