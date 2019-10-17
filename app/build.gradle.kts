plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    dataBinding {
        isEnabled = true
    }
    defaultConfig {
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        applicationId = "com.khiemle.wmovies"
        minSdkVersion(16)
        targetSdkVersion(28)
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.50")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.3.0-alpha02")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0-alpha02")

    implementation("androidx.recyclerview:recyclerview:1.0.0")

    implementation("io.reactivex.rxjava2:rxjava:2.2.1")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")

    implementation("com.squareup.okhttp3:logging-interceptor:3.10.0")
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.3.0")

    implementation("androidx.lifecycle:lifecycle-runtime:2.1.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.1.0")

    implementation("com.google.dagger:dagger:2.16")
    implementation("com.google.dagger:dagger-android:2.16")
    implementation("com.google.dagger:dagger-android-support:2.16")
    kapt("com.google.dagger:dagger-compiler:2.16")
    kapt("com.google.dagger:dagger-android-processor:2.16")

    implementation("com.github.bumptech.glide:glide:4.8.0")
    kapt("com.github.bumptech.glide:compiler:4.7.1")

    implementation("androidx.room:room-runtime:2.2.0")
    kapt("androidx.room:room-compiler:2.2.0")
    implementation("androidx.room:room-rxjava2:2.2.0")

    implementation("androidx.paging:paging-runtime:2.1.0")
    implementation("androidx.paging:paging-rxjava2:2.1.0")

}
