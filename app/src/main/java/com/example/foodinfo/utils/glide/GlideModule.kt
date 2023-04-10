package com.example.foodinfo.utils.glide

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import com.example.foodinfo.utils.glide.svg.SVGModel
import com.example.foodinfo.utils.glide.svg.SVGDrawableTranscoder
import com.example.foodinfo.utils.glide.svg.SVGLocalDecoder
import com.example.foodinfo.utils.glide.svg.SVGModelLoaderFactory
import com.example.foodinfo.utils.glide.svg.SVGRemoteDecoder
import java.io.InputStream


/**
 * Glide module that can handle both variants of SVG formats: raw string and URL
 */
@GlideModule
class GlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry
            .register(
                SVG::class.java,
                BitmapDrawable::class.java,
                SVGDrawableTranscoder()
            )
            .append(
                InputStream::class.java,
                SVG::class.java,
                SVGRemoteDecoder()
            )
            .append(
                SVGModel::class.java,
                SVG::class.java,
                SVGLocalDecoder()
            )
            .prepend(
                SVGModel::class.java,
                SVGModel::class.java,
                SVGModelLoaderFactory()
            )
    }
}