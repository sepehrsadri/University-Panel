// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  val compose_version by extra("1.0.0-beta08")
  val hilt_version by extra("2.35.1")
  repositories {
    google()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
      content {
        includeModule("com.google.dagger", "hilt-android-gradle-plugin")
      }
    }
    mavenCentral()
  }
  dependencies {
    classpath ("com.android.tools.build:gradle:7.0.0-beta03")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    classpath("com.google.dagger:hilt-android-gradle-plugin:HEAD-SNAPSHOT")
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle.kts files
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}