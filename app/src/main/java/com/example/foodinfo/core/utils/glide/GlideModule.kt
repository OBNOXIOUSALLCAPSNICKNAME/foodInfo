package com.example.foodinfo.core.utils.glide

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.caverock.androidsvg.SVG
import com.example.foodinfo.R
import com.example.foodinfo.core.utils.glide.svg.*
import java.io.InputStream


//TODO rework GlideModule to accept string (with overriding handles() method) and replace SVGModel with String
/**
 * Glide module that can handle both variants of SVG formats: raw string and URL
 */
@GlideModule
class GlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_no_image)
        )
        super.applyOptions(context, builder)
    }

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