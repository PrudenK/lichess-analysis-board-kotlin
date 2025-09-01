import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    jacoco
}

kotlin {
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Corrutinas
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

            // Retrofit
            implementation("com.squareup.retrofit2:retrofit:2.11.0")

            // Gson
            implementation("com.google.code.gson:gson:2.11.0")
            implementation("com.squareup.retrofit2:converter-gson:2.11.0")


            implementation("org.jsoup:jsoup:1.17.2")


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}


compose.resources {
    publicResClass = true
}

compose.desktop {
    application {
        mainClass = "org.pruden.tablero.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.pruden.tablero"
            packageVersion = "1.0.0"
        }
    }
}


tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("jvmTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    val sourceDirs = files("composeApp/src/jvmMain/kotlin")
    val classDirs = fileTree("composeApp/build/classes/kotlin/jvm/main") {
        exclude("**/R.class", "**/R\$*.class")
    }
    val execDataFiles = fileTree("composeApp/build") {
        include("**/jacoco/test.exec")
    }

    sourceDirectories.setFrom(sourceDirs)
    classDirectories.setFrom(classDirs)
    executionData.setFrom(execDataFiles)
}
