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
  implementation("com.squareup.retrofit2:retrofit:2.11.0")

  // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
  implementation("com.squareup.retrofit2:converter-gson:2.11.0")

// https://mvnrepository.com/artifact/com.google.code.gson/gson
  implementation("com.google.code.gson:gson:2.12.1")

  // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-scalars
  implementation("com.squareup.retrofit2:converter-scalars:2.11.0")

//  리사이클러 뷰
  implementation ("androidx.recyclerview:recyclerview:1.2.1")

  implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")


}