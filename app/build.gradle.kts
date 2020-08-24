import com.khiemle.builder.AndroidSDK
import com.khiemle.builder.MoviesExtension

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.khiemle.builder")
}


configure<MoviesExtension> {
    applicationId = "com.khiemle.auto"
    name = "Auto Create Movie"
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
    flavorDimensions("channel", "version")
    productFlavors {
        create("full") {

            applicationIdSuffix = ".full"
            versionNameSuffix = "-full"
            setDimension("version")

        }
        create("demo") {

            applicationIdSuffix = ".demo"
            versionNameSuffix = "-demo"
            setDimension("version")

        }
        create("wizemovie") {
            setDimension("channel")
            applicationId = "com.khiemle.wizemovie"
            resValue("string", "gen_app_name", "Wizeline Movie")

        }
    }
//    run {
//        android.productFlavors.register("lite") {
//            this.setDimension("version")
//            this.applicationIdSuffix = ".lite"
//            this.versionNameSuffix = "-lite"
//        }
//        android.productFlavors.register("auto") {
//            this.setDimension("channel")
//        }
//        android.productFlavors.forEach {
//            println(it?.name)
//        }
//
//        val builder = plugins.getPlugin(BuilderPlugin::class)
//
//        builder.let {
//            builder.listApps.forEach {config ->
//                config.name?.let { name ->
//                    android.productFlavors.register(name) {
//                        this.setDimension("channel")
//                        config.applicationId?.let {appId ->
//                            applicationId = appId
//                        }
//                        resValue("string", "gen_app_name", name)
//                    }
//                }
//
//            }
//
//        }
//
//    }
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
    testImplementation(Libs.Test.Mockito.mockito)
    androidTestImplementation(Libs.Test.Android.AndroidX.Runner.runner)
    androidTestImplementation(Libs.Test.Android.AndroidX.Rules.rules)
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

tasks.register("createNewFlavors") {
    doFirst {
        println("doFirstOfTask")
    }

    doLast {
        println("doLastOfTask")
    }

}

repeat(4) {
    tasks.register("task$it") {
        doLast {
            println("I'm task number $it")
        }
    }
}