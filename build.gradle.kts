import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList.arguments
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("kapt") version "1.4.31" //(1)
    kotlin("plugin.serialization") version "1.4.31"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("org.postgresql:postgresql:42.3.1")
                implementation ("org.jetbrains.compose.material3:material3-desktop:1.2.0")
                implementation ("androidx.compose.material3:material3-window-size-class:1.0.1")
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Hall"
            packageVersion = "1.0.0"
        }
    }
}
