plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "bitc.example.app"
  compileSdk = 35

  defaultConfig {
    applicationId = "bitc.example.app"
    minSdk = 29
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }

  viewBinding {
    enable = true
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
  implementation(libs.retrofit)
  // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
  implementation(libs.converter.gson)
  // https://mvnrepository.com/artifact/com.google.code.gson/gson
  implementation(libs.gson)
  // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-scalars
  implementation(libs.converter.scalars)
}