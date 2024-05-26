plugins {
    alias(libs.plugins.androidApplication)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

}

android {
    namespace = "com.velasquez.crud_firebasev1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.velasquez.crud_firebasev1"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")


    //implementation("com.google.firebase:firebase-core:9.6.1")

    //Database
    implementation("com.google.firebase:firebase-database:19.2.0")

    // Use AndroidX libraries
    implementation("com.google.android.material:material:1.6.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
