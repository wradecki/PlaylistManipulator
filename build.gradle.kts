import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha3"
}

group = "me.xxslayerxx"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.amshove.kluent:kluent:1.68")
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    // https://mvnrepository.com/artifact/uk.co.caprica/vlcj
    implementation("uk.co.caprica:vlcj:4.7.1")
    implementation("br.com.devsrsouza.compose.icons.jetbrains:font-awesome:1.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "16"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "PlaylistManipulator"
            packageVersion = "1.0.0"
        }
    }
}