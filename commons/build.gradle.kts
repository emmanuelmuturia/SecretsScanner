/*
 * Copyright 2025 Secrets Scanner
 *
 * Licenced under the Apache License, Version 2.0 (the "Licence");
 * you may not use this file except in compliance with the Licence.
 * You may obtain a copy of the Licence at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(notation = libs.plugins.kotlinMultiplatform)
    alias(notation = libs.plugins.androidLibrary)
    alias(notation = libs.plugins.ksp)
    alias(notation = libs.plugins.kotlin.serialization.json)
    alias(notation = libs.plugins.room)
    alias(notation = libs.plugins.compose.compiler)
    alias(notation = libs.plugins.composeMultiplatform)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "commons"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(dependencyNotation = libs.koin.android)
            implementation(dependencyNotation = libs.koin.androidx.compose)
            implementation(dependencyNotation = libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            // Put your Multiplatform Dependencies here...
            implementation(dependencyNotation = compose.runtime)
            implementation(dependencyNotation = compose.foundation)
            implementation(dependencyNotation = compose.material3)
            implementation(dependencyNotation = compose.ui)
            implementation(dependencyNotation = compose.materialIconsExtended)
            api(dependencyNotation = libs.koin.core)
            implementation(dependencyNotation = libs.koin.compose)
            implementation(dependencyNotation = libs.koin.compose.viewmodel)
            implementation(dependencyNotation = libs.ktor.client.core)
            implementation(dependencyNotation = libs.ktor.client.content.negotiation)
            implementation(dependencyNotation = libs.ktor.serialization.kotlinx.json)
            implementation(dependencyNotation = libs.kotlinx.coroutines.core)
            implementation(dependencyNotation = libs.timber)
            implementation(dependencyNotation = libs.lifecycle.viewmodel)
            implementation(dependencyNotation = libs.kotlin.serialization.json)
            implementation(dependencyNotation = libs.room.runtime)
            implementation(dependencyNotation = libs.sqlite.bundled)
            implementation(dependencyNotation = libs.landscapist.coil3)
            implementation(dependencyNotation = libs.voyager.navigator)
        }
        commonTest.dependencies {
            implementation(dependencyNotation = libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(dependencyNotation = libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "emmanuelmuturia.secretsscanner"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

room {
    schemaDirectory(path = "$projectDir/schemas")
}

dependencies {
    add(configurationName = "kspAndroid", dependencyNotation = libs.room.compiler)
    add(configurationName = "kspIosX64", dependencyNotation = libs.room.compiler)
    add(configurationName = "kspIosArm64", dependencyNotation = libs.room.compiler)
    add(configurationName = "kspIosSimulatorArm64", dependencyNotation = libs.room.compiler)
}