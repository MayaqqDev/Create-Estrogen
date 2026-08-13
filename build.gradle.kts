@file:Suppress("PropertyName", "UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.modpublish)
    alias(libs.plugins.cloche)
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.serialization") version libs.versions.kotlin
}

repositories {
    cloche {
        librariesMinecraft()
        mavenNeoforged()
        mavenForge()
        mavenFabric()
        mavenNeoforgedMeta()
        mavenParchment()
    }
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
    maven(url = "https://maven.teamresourceful.com/repository/maven-public/") { name = "Team Resourceful" }
    maven(url = "https://maven.createmod.net/") { name = "Create" }
    maven(url = "https://maven.ithundxr.dev/snapshots")
    maven(url = "https://maven.is-immensely.gay/nightly") { name = "Sappho Company"; description = "Critter, Cynosure" }
    maven(url = "https://maven.is-immensely.gay/releases") { name = "Sappho Company"; description = "Kittyconfig" }
    maven(url = "https://api.modrinth.com/maven") { name = "Modrinth Maven"; description = "Jukeboxfix, Ad Astra" }
    maven(url = "https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") { name = "DevAuth maven"; description = "DevAuth" }
    maven(url ="https://maven.latvian.dev/releases") {name = "latvian.dev"; description = "KubeJS" }
    maven(url = "https://maven.shedaniel.me") { name = "Shedaniel" }
    maven(url = "https://jitpack.io"){
        content {
            includeGroup("com.github.rtyley")
        }
    }
    mavenLocal()
    mavenCentral()
}

val item_viewer: String = providers.gradleProperty("item_viewer").get()
val modVersion = providers.gradleProperty("version").get()
val mod_name: String  = providers.gradleProperty("mod_name").get()

val kubejs_enabled: String = providers.gradleProperty("kubejs_enabled").get()
val devauth_enabled: String = providers.gradleProperty("devauth_enabled").get()

cloche {
    metadata {
        modId = "createestrogen"
        name = "Create: Estrogen"
        description = "Create module for Estrogen"
        license = "LGPL-3.0"
        icon = "icon.png"
        url = "https://github.com/MayaqqDev/Create-Estrogen"
        sources = "https://github.com/MayaqqDev/Create-Estrogen"
        author("Mayaqq")
        contributor("https://modded.wiki/w/Estrogen:Credits")
        dependency { modId = "create"; version { start = "6.0.6" } }
        dependency { modId = "cynosure"; version { start = "0.1.16" } }
        dependency { modId = "estrogen"; version { start = "5.0.8" } }
        dependency { modId = "create"; version { start = "6.0.6" }
        }
    }

    mappings {
        official()
        parchment(libs.versions.parchment)
    }

    singleTarget {
        neoforge {
            mixins.from(file("src/main/createestrogen.mixins.json"))
            datagenDirectory.set(file("build/generated/resources/neoforge"))
            loaderVersion = libs.versions.neoforge.get()
            minecraftVersion = libs.versions.minecraft.get()

            metadata {
                modLoader = "kritter"
                loaderVersion {
                    startInclusive = true
                    start = "1"
                }
                blurLogo = false
            }

            data {
                dependencies {
                    modApi("dev.mayaqq:estrogen:${libs.versions.estrogen}:neoforge")
                }
            }
            runs {
                this.data() {
                    prop("neoforge.enabledGameTestNamespaces", "createestrogen")
                }
                client() {
                    /// cloche is too dumb to do this for us, this makes it so that the run all tests command doesn't run create's tests
                    prop("neoforge.enabledGameTestNamespaces", "createestrogen")

                }
                server() {
                    prop("neoforge.enabledGameTestNamespaces", "createestrogen")
                }
            }

            dependencies {
                // Common
                compileOnly(libs.mixin)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                modApi(libs.cynosure)
                modApi(libs.estrogen) {
                    this.artifact { classifier = "neoforge" }
                }
                modApi(libs.lattice)
                modApi(libs.forge.kubejs)
                modCompileOnly(libs.forge.registrate)

                // Neoforge
                modImplementation(libs.forge.kotlin)
                modCompileOnlyApi(libs.forge.flywheel.api)
                modImplementation(libs.forge.flywheel)
                modCompileOnly(libs.forge.rei)
                modCompileOnly(libs.forge.jei)
                modCompileOnly(libs.forge.emi)
                modApi(libs.forge.kritter)
                modApi(libs.forge.create)
                modApi(libs.forge.rlib)
                modApi(libs.forge.csr)
                modApi(libs.forge.ponder)
                modApi(libs.forge.registrate)

                when(item_viewer) {
                    "EMI" -> modRuntimeOnly(libs.forge.emi)
                    "REI" -> modRuntimeOnly(libs.forge.rei)
                    "JEI" -> modRuntimeOnly(libs.forge.jei)
                    "disabled" -> {}
                    else -> error("Invalid item viewer for NeoForge: $item_viewer")
                }
                if (kubejs_enabled.toBoolean()) modRuntimeOnly(libs.forge.kubejs)

                if (devauth_enabled.toBoolean()) modRuntimeOnly(libs.forge.devauth)
            }
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}

kotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_4
        freeCompilerArgs.addAll(
            "-Xjvm-default=all-compatibility",
            "-Xmulti-platform",
            "-Xno-check-actual",
            "-Xexpect-actual-classes",
            "-XXLanguage:+ExpectRefinement"
        )
    }
    jvmToolchain(21)
}

publishMods {
    changelog = file("CHANGELOG.md").readText().replace("@VERSION@", modVersion)
    type = STABLE
    val loader = "neoforge"
    val jar = tasks.named<Jar>("includeJar").flatMap(Jar::getArchiveFile)

    curseforge("curseforge") {
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.add("1.21.1")
        projectId = "1272015"
        javaVersions.add(JavaVersion.VERSION_21)
        clientRequired = true
        serverRequired = true
        modLoaders.add(loader)
        file = jar
        displayName = "$mod_name $modVersion NeoForge"
        version = "$modVersion-$loader"
        requires("estrogen", "create")
    }

    modrinth("modrinth") {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = "OEAJaSuI"
        minecraftVersions.add("1.21.1")
        modLoaders.add(loader)
        file = jar
        displayName = "$mod_name $modVersion NeoForge"
        version = "$modVersion-$loader"
        requires("estrogen", "create")
    }
}