object Libs {

    object Gradle {
        private const val version = "4.0.1"
        const val android = "com.android.tools.build:gradle:$version"
    }

    object Kotlin {
        const val version = "1.3.50"
        const val gradlePlugin = "gradle-plugin"
        const val stdlibJDK7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object ReactiveX {
        object RxJava2 {
            const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.9"
            const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.0"
        }
    }

    object Network {
        object OkHttp3 {
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.10.0"
        }
        object Retrofit2 {
            private const val version = "2.5.0"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val convertGson = "com.squareup.retrofit2:converter-gson:2.4.0"
            object Adapter {
                const val rxJava2 = "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
            }
        }
    }

    object Glide {
        private const val version = "4.8.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:4.7.1"
    }

    object Test {
        object JUnit {
            private const val version = "4.12"
            const val junit = "junit:junit:$version"
        }
        object Mockito {
            private const val version = "2.2.0"
            const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:$version"
        }
        object Android {
            object AndroidX {
                object Runner {
                    private const val version = "1.2.0"
                    const val runner = "androidx.test:runner:$version"
                }
                object Rules {
                    private const val version = "1.2.0"
                    const val rules = "androidx.test:rules:$version"
                }
                object Espresso {
                    private const val version = "3.2.0"
                    const val core = "androidx.test.espresso:espresso-core:$version"
                }
            }
        }
    }

    object Google  {
        object Material {
            private const val version = "1.0.0"
            const val material = "com.google.android.material:material:$version"
        }
        object Dagger {
            private const val version = "2.16"
            const val dagger = "com.google.dagger:dagger:$version"
            const val daggerAndroid = "com.google.dagger:dagger-android:$version"
            const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$version"
            const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
            const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$version"

        }
    }

    object AndroidJetPack {
        object AndroidX {
            object AppCompat {
                private const val version = "1.1.0"
                const val appCompat = "androidx.appcompat:appcompat:$version"
            }

            object ConstraintLayout {
                private const val version = "1.1.3"
                const val constraintLayout = "androidx.constraintlayout:constraintlayout:$version"
            }

            object VectorDrawable {
                private const val version = "1.1.0"
                const val vectorDrawable = "androidx.vectordrawable:vectordrawable:$version"
            }

            object RecyclerView {
                private const val version = "1.0.0"
                const val recyclerView = "androidx.recyclerview:recyclerview:$version"
            }

            object LifeCycle {
                private const val version = "2.1.0"
                const val runtime = "androidx.lifecycle:lifecycle-runtime:$version"
                const val extension = "androidx.lifecycle:lifecycle-extensions:$version"
                const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            }
            object Room {
                private const val version = "2.2.0"
                const val runtime = "androidx.room:room-runtime:$version"
                const val compiler = "androidx.room:room-compiler:$version"
                const val rxJava2 = "androidx.room:room-rxjava2:$version"
            }
            object Paging {
                private const val version = "2.1.0"
                const val runtime = "androidx.paging:paging-runtime:$version"
                const val rxJava2 = "androidx.paging:paging-rxjava2:$version"
            }
        }


    }

}