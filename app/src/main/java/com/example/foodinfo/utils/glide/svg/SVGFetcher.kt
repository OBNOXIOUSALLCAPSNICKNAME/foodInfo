package com.example.foodinfo.utils.glide.svg

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher

class SVGFetcher(private var svg: SVGModel) : DataFetcher<SVGModel> {
    override fun loadData(
        priority: Priority,
        callback: DataFetcher.DataCallback<in SVGModel>
    ) {
        callback.onDataReady(svg)
    }

    override fun cleanup() {}

    override fun cancel() {}

    override fun getDataClass(): Class<SVGModel> {
        return SVGModel::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.LOCAL
    }

}