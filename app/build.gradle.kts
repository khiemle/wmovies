import com.khiemle.builder.AndroidSDK

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.khiemle.builder")
}

android {
    compileSdkVersion(AndroidSDK.compileSdk)
    dataBinding {
        isEnabled = true
    }
    defaultConfig {
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        applicationId = "com.khiemle.wmovies"
        minSdkVersion(AndroidSDK.minSdk)
        targetSdkVersion(AndroidSDK.targetSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Kotlin.stdlibJDK7)
    implementation(Libs.Kotlin.reflect)
    implementation(Libs.AndroidJetPack.AndroidX.AppCompat.appCompat)
    implementation(Libs.Google.Material.material)
    implementation(Libs.AndroidJetPack.AndroidX.ConstraintLayout.constraintLayout)
    implementation(Libs.AndroidJetPack.AndroidX.VectorDrawable.vectorDrawable)
    testImplementation(Libs.Test.JUnit.junit)
    androidTestImplementation(Libs.Test.Android.AndroidX.Runner.runner)
    androidTestImplementation(Libs.Test.Android.AndroidX.Espresso.core)

    implementation(Libs.AndroidJetPack.AndroidX.RecyclerView.recyclerView)

    implementation(Libs.ReactiveX.RxJava2.rxJava)
    implementation(Libs.ReactiveX.RxJava2.rxAndroid)

    implementation(Libs.Network.OkHttp3.loggingInterceptor)
    implementation(Libs.Network.Retrofit2.retrofit)
    implementation(Libs.Network.Retrofit2.convertGson)
    implementation(Libs.Network.Retrofit2.Adapter.rxJava2)

    implementation(Libs.AndroidJetPack.AndroidX.LifeCycle.runtime)
    implementation(Libs.AndroidJetPack.AndroidX.LifeCycle.extension)
    kapt(Libs.AndroidJetPack.AndroidX.LifeCycle.compiler)

    implementation(Libs.Google.Dagger.dagger)
    implementation(Libs.Google.Dagger.daggerAndroid)
    implementation(Libs.Google.Dagger.daggerAndroidSupport)

    kapt(Libs.Google.Dagger.daggerCompiler)
    kapt(Libs.Google.Dagger.daggerAndroidProcessor)

    implementation(Libs.Glide.glide)
    kapt(Libs.Glide.compiler)

    implementation(Libs.AndroidJetPack.AndroidX.Room.runtime)
    kapt(Libs.AndroidJetPack.AndroidX.Room.compiler)
    implementation(Libs.AndroidJetPack.AndroidX.Room.rxJava2)

    implementation(Libs.AndroidJetPack.AndroidX.Paging.runtime)
    implementation(Libs.AndroidJetPack.AndroidX.Paging.rxJava2)

}

tasks.register("intro") {
    doLast {
        println("This is Wmovies")
    }
}

tasks.register("printAppHelloWorld") {
    dependsOn("intro")
    doLast {
        println("Hello World from App")
    }
}

repeat(4) {
    tasks.register("task$it") {
        doLast {
            println("I'm task number $it")
        }
    }
}