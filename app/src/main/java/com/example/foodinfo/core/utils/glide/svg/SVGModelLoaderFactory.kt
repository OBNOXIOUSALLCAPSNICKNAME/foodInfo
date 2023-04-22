package com.example.foodinfo.core.utils.glide.svg

import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory

class SVGModelLoaderFactory : ModelLoaderFactory<SVGModel, SVGModel> {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<SVGModel, SVGModel> {
        return SVGModelLoader()
    }

    override fun teardown() {}
}