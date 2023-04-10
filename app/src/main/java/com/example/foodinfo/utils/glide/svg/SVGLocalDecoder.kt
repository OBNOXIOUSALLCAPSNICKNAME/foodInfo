package com.example.foodinfo.utils.glide.svg

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import java.io.IOException

class SVGLocalDecoder : ResourceDecoder<SVGModel, SVG> {
    override fun handles(source: SVGModel, options: Options): Boolean {
        return true
    }

    @Throws(IOException::class)
    override fun decode(
        source: SVGModel, width: Int, height: Int, options: Options
    ): Resource<SVG> {
        try {
            val svg = SVG.getFromInputStream(source.content.byteInputStream())
            return SimpleResource(svg)
        } catch (ex: SVGParseException) {
            throw IOException("Cannot load SVG from stream", ex)
        }
    }
}