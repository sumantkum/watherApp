plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 35

    buildFeatures {
        viewBinding = true

    }

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

// Define versions in one place for easier management
val lottieVersion = "6.0.0"
val retrofitVersion = "2.9.0"
val junitVersion = "4.13.2" // Example version, update as needed

dependencies {
    // AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.junit.junit)
    implementation(libs.androidx.junit.ktx)

    // Testing Libraries
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Lottie Animation
    implementation("com.airbnb.android:lottie:$lottieVersion")

    // Retrofit for Networking
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Other dependencies...

    // Espresso core
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Espresso intents
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    // Espresso contrib (optional, for additional features)
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    // AndroidX Test rules
    androidTestImplementation("androidx.test:rules:1.5.0")
    // AndroidX Test runner
    androidTestImplementation("androidx.test:runner:1.5.0")
}