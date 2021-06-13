plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-android-extensions")
  id("dagger.hilt.android.plugin")
  id("androidx.navigation.safeargs")
}

android {
  compileSdk = 30
  buildToolsVersion = "30.0.3"

  defaultConfig {
    applicationId = "com.sadri.universitypanel"
    minSdk = 21
    targetSdk = 30
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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
    useIR = true
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
  }
}

dependencies {

  implementation("androidx.core:core-ktx:1.3.0")
  implementation("androidx.appcompat:appcompat:1.3.0")
  implementation("com.google.android.material:material:1.3.0")
  implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
  implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
  implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
  implementation("androidx.activity:activity-compose:1.3.0-alpha07")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.2")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
  implementation ("androidx.compose.runtime:runtime-livedata:1.0.0-beta08")

  // Hilt
  implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt_version"]}")
  kapt("com.google.dagger:hilt-compiler:${rootProject.extra["hilt_version"]}")
  kapt ("androidx.hilt:hilt-compiler:1.0.0")
  implementation ("androidx.hilt:hilt-navigation-compose:1.0.0-alpha02")

  // Networking
  val retrofitVersion = "2.7.1"
  implementation("com.google.code.gson:gson:2.8.6")
  implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
  implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
  implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

  // Navigation
  val navigationVersion = "2.3.4"
  implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
  implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
  implementation("androidx.navigation:navigation-compose:2.4.0-SNAPSHOT")


  // Logger
  implementation("com.jakewharton.timber:timber:4.7.1")

  // Persistence
  implementation("androidx.datastore:datastore-preferences:1.0.0-beta01")

}