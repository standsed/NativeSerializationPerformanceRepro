plugins {
    kotlin("multiplatform") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
}
repositories {
    mavenCentral()
    maven ( url = "https://kotlin.bintray.com/kotlinx" )
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

group = "com.example"
version = "0.0.1"
val serializationVersion = "1.0.0-RC"

kotlin {
    jvm()
    iosX64()
    macosX64()


    macosX64().binaries.getTest("DEBUG").freeCompilerArgs += arrayOf("-opt", "-Xallocator=mimalloc")
    iosX64().binaries.getTest("DEBUG").freeCompilerArgs += arrayOf("-opt", "-Xallocator=mimalloc")

    sourceSets {
        val iosX64Main by getting {
            dependencies {
            }
        }

        val macosX64Main by getting {
            dependencies {
            }
        }

        val jvmMain by getting {
            dependencies {
            }
        }

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("junit:junit:4.12")
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }
    }
}