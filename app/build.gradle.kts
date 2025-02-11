plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") //firebase
}

android {
    namespace = "cat.dam.dishdiscovery"
    compileSdk = 34

    defaultConfig {
        applicationId = "cat.dam.dishdiscovery"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("io.coil-kt:coil-compose:1.4.0")
    implementation ("androidx.compose.ui:ui:1.6.3")
    implementation ("androidx.compose.material:material:1.6.3")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.6")
    implementation ("com.google.android.gms:play-services-maps:17.0.1") // google maps
    implementation ("com.google.maps.android:maps-compose-utils:4.2.0")// google maps
    implementation ("com.google.maps.android:maps-compose-widgets:4.2.0")// google maps
    implementation ("com.google.android.gms:play-services-location:21.2.0") // google maps

    implementation("com.google.maps.android:maps-compose:4.2.0") // google maps

    implementation ("androidx.appcompat:appcompat:1.4.1")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1")) //firebase
    implementation("com.google.firebase:firebase-appcheck-playintegrity")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.2") //coroutines play services

    implementation("com.google.firebase:firebase-analytics") //firebase analytics
    implementation("com.google.firebase:firebase-firestore") //firebase firestore
    implementation("com.google.firebase:firebase-auth:22.3.1") //firebase authentication
    implementation("com.google.firebase:firebase-storage:20.3.0") //firebase storage
    implementation("com.google.android.gms:play-services-auth:21.0.0") //google sign in

    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation("androidx.compose.material3:material3-android:1.2.0")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("com.google.firebase:firebase-analytics") //firebase
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1") //firebase
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}