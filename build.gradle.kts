import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
    id("org.openjfx.javafxplugin") version "0.0.7"
}

group = "world.gregs.image.palette"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://dl.bintray.com/mateuszlisik/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.1.1")
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("de.androidpit:color-thief-java:1.0")
}

val javafxModules = arrayOf("controls", "swing")

javafx {
    modules = javafxModules.map { "javafx.$it" }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}