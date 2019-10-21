plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
}