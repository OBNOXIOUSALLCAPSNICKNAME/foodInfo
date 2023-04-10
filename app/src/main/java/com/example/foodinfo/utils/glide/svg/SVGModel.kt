package com.example.foodinfo.utils.glide.svg

import com.example.foodinfo.utils.glide.GlideModule


/**
 * Wrapper for SVG string that used in Glide.load() which also tells [GlideModule] to use proper decoders.
 */
data class SVGModel(val content: String)