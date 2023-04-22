package com.example.foodinfo.core.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.example.foodinfo.BaseApplication
import com.example.foodinfo.BaseApplicationComponent
import org.json.JSONObject


val Context.appComponent: BaseApplicationComponent
    get() = when (this) {
        is BaseApplication -> applicationComponent
        else               -> this.applicationContext.appComponent
    }

@ColorInt
fun Context.getAttrColor(
    @AttrRes
    attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.openAsset(fileName: String): JSONObject {
    val asset = this.assets.open(fileName)
    val size = asset.available()
    val buffer = ByteArray(size)
    asset.read(buffer)
    asset.close()
    return JSONObject(String(buffer, Charsets.UTF_8))
}

fun Context.hasInternet(): Boolean {
    val connectivityManager = getSystemService(ConnectivityManager::class.java)
    val currentNetwork = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
    capabilities?.let {
        if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        ) {
            return true
        }
    }
    return false
}