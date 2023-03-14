plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

kapt {
    generateStubs = true
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.foodinfo"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "API_GITHUB", "\"https://api.github.com/\"")
            buildConfigField("String", "API_EDAMAM", "\"https://api.edamam.com/api/recipes/v2/\"")
        }
        getByName("release") {
            buildConfigField("String", "API_GITHUB", "\"https://api.github.com/\"")
            buildConfigField("String", "API_EDAMAM", "\"https://api.edamam.com/api/recipes/v2/\"")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
    lint {
        abortOnError = false
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    implementation(Dependencies.RemoteData.gson)
    implementation(Dependencies.RemoteData.gsonConverter)
    implementation(Dependencies.RemoteData.retrofit)

    kapt(Dependencies.DI.daggerCompiler)
    implementation(Dependencies.DI.dagger)
    implementation(Dependencies.DI.daggerAndroid)

    implementation(Dependencies.MultiThreading.coroutines)
    implementation(Dependencies.MultiThreading.coroutinesCore)

    kapt(Dependencies.LocalData.roomCompiler)
    implementation(Dependencies.LocalData.roomKTX)
    implementation(Dependencies.LocalData.roomPaging)
    implementation(Dependencies.LocalData.roomRuntime)

    kapt(Dependencies.UI.glideCompiler)
    implementation(Dependencies.UI.glide)
    implementation(Dependencies.UI.paging)
    implementation(Dependencies.UI.svg)
    implementation(Dependencies.UI.core)
    implementation(Dependencies.UI.appcompat)
    implementation(Dependencies.UI.material)
    implementation(Dependencies.UI.constraint)
    implementation(Dependencies.UI.lifecycleViewModel)
    implementation(Dependencies.UI.lifecycleRuntime)
    implementation(Dependencies.UI.navigationUI)
    implementation(Dependencies.UI.navigationFragment)

    //    debugImplementation(Dependencies.Other.leakcanary)
    implementation(Dependencies.Other.legacySupport)
    implementation(Dependencies.Other.preference)
    testImplementation(Dependencies.Other.junit)
    androidTestImplementation(Dependencies.Other.junitExt)
    androidTestImplementation(Dependencies.Other.espresso)
}