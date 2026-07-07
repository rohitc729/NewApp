import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization.plugin)
    alias(libs.plugins.hilt)
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.newsapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.newsapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
val secretProperties= Properties()
    val secreteFile= rootProject.file("secret.properties")
    if(secreteFile.exists() && secreteFile.isFile){
        secreteFile.inputStream().use { input->
            secretProperties.load(input)
        }
    }
    buildTypes {
        release {
            isMinifyEnabled=true
            isShrinkResources=true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String","API_KEY",secretProperties.getProperty("API_KEY"))
        }
        debug {
            buildConfigField("String","API_KEY",secretProperties.getProperty("API_KEY"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")
    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    //COIL
    implementation(libs.coil.compose)
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")
    //compose navigation
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.serialization)
    //horizontal pager
    implementation("androidx.compose.foundation:foundation")
}