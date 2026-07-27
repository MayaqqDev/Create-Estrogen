@file:Suppress("PropertyName", "UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.cloche)
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.serialization") version libs.versions.kotlin
}

repositories {
    cloche {
        librariesMinecraft()
        mavenNeoforged()
        mavenNeoforgedMeta()
        mavenParchment()
    }
    maven("https://maven.is-immensely.gay/nightly")
    maven("https://maven.is-immensely.gay/releases")
    maven("https://maven.createmod.net")
    maven("https://maven.ithundxr.dev/snapshots")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.teamresourceful.com/repository/maven-public/")
    maven("https://maven.latvian.dev/releases")
    maven("https://maven.blamejared.com")
    maven("https://maven.shedaniel.me")
    maven("https://api.modrinth.com/maven")
    maven("https://jitpack.io")
    mavenCentral()
    mavenLocal()
}

val itemViewer = providers.gradleProperty("item_viewer").get()
val devAuthEnabled = providers.gradleProperty("devauth_enabled").get().toBoolean()

val estrogenJarOverride = providers.gradleProperty("estrogen_jar")

cloche {
    metadata {
        modId = "createestrogen"
        name = "Create: Estrogen"
        description = "Create integration module for Estrogen"
        license = "LGPL-3.0"
        icon = "icon.png"
        url = "https://github.com/MayaqqDev/Create-Estrogen"
        sources = "https://github.com/MayaqqDev/Create-Estrogen"
        author("Mayaqq")
        contributor("https://modded.wiki/w/Estrogen:Credits")

        dependency {
            modId = "create"
            version { start = "6.0.10" }
        }
        dependency {
            modId = "cynosure"
            version { start = "1.0.0" }
        }
        dependency {
            modId = "estrogen"
            version { start = "5.0.8" }
        }
    }

    mappings {
        official()
        parchment(libs.versions.parchment)
    }

    singleTarget {
        neoforge {
        mixins.from(
            file("src/main/createestrogen.mixins.json"),
            file("src/neoforge/createestrogen-neoforge.mixins.json")
        )
        accessWideners.from(file("src/main/createestrogen.accessWidener"))
        loaderVersion = libs.versions.neoforge.get()
        minecraftVersion = libs.versions.minecraft.get()

        metadata {
            modLoader = "kritter"
            loaderVersion {
                start = "1"
                end = "999"
            }
            blurLogo = false
        }

        runs {
            client {
                runDir("run")
            }
            server {
                runDir("runServer")
                jvmArgs("--nogui")
            }
        }

        dependencies {
            compileOnly(libs.mixin)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.flywheel.api)
            implementation(libs.mixinExtras)
            annotationProcessor(libs.mixinExtras)
            modApi(libs.cynosure)
            api(libs.kotlinforforge)
            modApi(libs.kritter)
            if (estrogenJarOverride.isPresent) {
                modImplementation(files(estrogenJarOverride.get()))
            } else {
                modImplementation(libs.estrogen)
            }
            modImplementation(libs.create) {
                artifact { classifier = "slim" }
                isTransitive = false
            }
            modImplementation(libs.ponder)
            modImplementation(libs.flywheel)
            modCompileOnlyApi(libs.flywheel.neoforge.api)
            modImplementation(libs.registrate)
            modImplementation(libs.csr.neoforge)
            modImplementation(libs.rlib)
            legacyClasspath(libs.cosmetics)
            implementation(libs.mixinExtras.neoforge)
            modRuntimeOnly(libs.curios)

            when (itemViewer) {
                "EMI" -> modRuntimeOnly(libs.emi)
                "REI" -> modRuntimeOnly(libs.rei)
                "JEI" -> modRuntimeOnly(libs.jei)
                "disabled" -> Unit
                else -> error("Invalid item viewer for NeoForge: $itemViewer")
            }
            if (devAuthEnabled) modRuntimeOnly(libs.devauth)
        }
        }
    }
}

java {
    withSourcesJar()
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

kotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_2
        freeCompilerArgs = listOf(
            "-Xmulti-platform",
            "-Xno-check-actual",
            "-Xexpect-actual-classes",
            "-Xcontext-parameters",
            "-opt-in=kotlin.uuid.ExperimentalUuidApi"
        )
    }
    jvmToolchain(21)
    sourceSets.named("main") {
        kotlin.srcDir("src/neoforge/kotlin")
        kotlin.exclude("dev/mayaqq/createestrogen/compat/kubejs/**")
        kotlin.exclude("dev/mayaqq/createestrogen/utils/recipe/CreateEstrogenCentrifugingRecipeBuilder.kt")
    }
}

sourceSets.named("main") {
    java.srcDir("src/neoforge/java")
    resources.srcDir("src/neoforge/resources")
}

// MinecraftCodev reads this while calculating the task graph, before writeModId
// can run in a clean checkout.
layout.buildDirectory.file("modId.txt").get().asFile.apply {
    parentFile.mkdirs()
    writeText(providers.gradleProperty("modid").get())
}

// Keep the remapper output separate from its intermediate input jar.
tasks.named<Jar>("remapJar") {
    destinationDirectory.set(layout.buildDirectory.dir("libs"))
}

tasks.named("runClient") {
    doFirst {
        layout.projectDirectory.dir("run").asFile.mkdirs()
    }
}
