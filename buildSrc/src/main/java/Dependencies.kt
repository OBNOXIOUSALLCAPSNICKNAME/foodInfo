object Dependencies {
    object UI {
        // image loading
        const val glide = "com.github.bumptech.glide:glide:${Versions.UI.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.UI.glide}"

        // SVG loading
        const val svg = "com.caverock:androidsvg-aar:${Versions.UI.svg}"

        // load paged data
        const val paging = "androidx.paging:paging-runtime-ktx:${Versions.UI.paging}"

        // base
        const val core = "androidx.core:core-ktx:${Versions.UI.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.UI.appcompat}"
        const val material = "com.google.android.material:material:${Versions.UI.material}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.UI.constraint}"

        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.UI.lifecycle}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.UI.lifecycle}"

        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.UI.navigation}"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.UI.navigation}"
    }

    object LocalData {
        const val roomCompiler = "androidx.room:room-compiler:${Versions.LocalData.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.LocalData.room}"
        const val roomPaging = "androidx.room:room-paging:${Versions.LocalData.room}"
        const val roomKTX = "androidx.room:room-ktx:${Versions.LocalData.room}"
    }

    object RemoteData {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RemoteData.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.RemoteData.retrofit}"
        const val gson = "com.google.code.gson:gson:${Versions.RemoteData.gson}"
    }

    object DI {
        const val dagger = "com.google.dagger:dagger:${Versions.DI.dagger}"
        const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.DI.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.DI.dagger}"
    }

    object MultiThreading {
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.MultiThreading.coroutines}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.MultiThreading.coroutines}"
    }

    object Other {
        const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.Other.legacySupport}"
        const val junit = "junit:junit:${Versions.Other.junit}"
        const val junitExt = "androidx.test.ext:junit:${Versions.Other.junitExt}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Other.espresso}"

        // track memory leaks
        const val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.Other.leakcanary}"
    }
}