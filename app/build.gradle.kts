plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.weatherapp.bib"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp.bib"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures{
        viewBinding { true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.volley)
    implementation (libs.gson)
    implementation(libs.constraintlayout)
    implementation(libs.glide)

}