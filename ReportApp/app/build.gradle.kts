plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.software.reportapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.software.reportapp"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

   viewBinding {
       enable = true
   }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // room db
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Fragment간의 원활한 전환을 위한 Jetpack 라이브러리 추가
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // 비동기 처리를 위한 Rxjava
    implementation(libs.rxjava3)
    implementation(libs.rxjava3.rxandroid)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}