package com.example.foodinfo.utils.glide.svg

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.signature.ObjectKey

class SVGModelLoader : ModelLoader<SVGModel, SVGModel> {
    override fun buildLoadData(
        model: SVGModel,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<SVGModel> {
        return ModelLoader.LoadData(
            ObjectKey("code:${model};width:$width;height:$height"),
            SVGFetcher(model)
        )
    }

    override fun handles(model: SVGModel): Boolean {
        return true
    }
}