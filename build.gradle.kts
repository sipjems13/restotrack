buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Firebase Services Plugin
        classpath("com.google.gms:google-services:4.3.15") // Firebase plugin
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}
