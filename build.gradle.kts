import org.gradle.api.publish.maven.MavenPom


group = "dev.tsune"
version = "0.1.0"

plugins {
    kotlin("multiplatform") version "1.7.21"

    // Apply plugin for document generation
    id("org.jetbrains.dokka") version "1.7.20"

    // Apply the java-library plugin for API and implementation separation.
    id("java-library")

    // Apply library publish plugin
    id("maven-publish")
}

repositories {
    // Use mavenCentral for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenCentral()
}

kotlin {
    explicitApiWarning()
    jvm {

    }
    js {
        browser {

        }
        nodejs {

        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}

kotlin {
    explicitApiWarning()
}

//sources
val sourcesJar by tasks.creating(Jar::class) {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

tasks.dokkaHtml.configure {
    doLast {
        val outputDir = outputDirectory.get().absolutePath
        File("$outputDir/index.html").apply {
            writeText("""
            <html><script>document.location = "./${project.name.map {
                if (it.isUpperCase()) "-" + it.toLowerCase()
                else it
            }.joinToString(separator = "")}"</script></html>
            """.trimIndent()
            )
        }
    }
}

// Create dokka Jar task from dokka task output
val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
    from(tasks.dokkaHtml)
}

publishing {
    fun getProperty(propertyName: String, envName: String): String? {
        return findProperty(propertyName) as? String ?: System.getenv(envName)
    }

    fun getBintrayUser(): String? {
        return getProperty("bintray_user", "BINTRAY_USER")
    }

    fun getBintrayKey(): String? {
        return getProperty("bintray_key", "BINTRAY_KEY")
    }
    fun MavenPom.initPom() {
        name.set("ktPyString")
        description.set("Python like String method in Kotlin")
        url.set("https://github.com/ChanTsune/ktPyString")

        licenses {
            license {
                name.set("MIT")
                url.set("https://github.com/ChanTsune/ktPyString/blob/master/LICENSE")
            }
        }
        scm {
            url.set("https://github.com/ChanTsune/ktPyString.git")
        }
    }
    publications {
        create<MavenPublication>("snapshot") {
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)
            artifactId = "ktPyString"
            version = "${project.version}-SNAPSHOT"

            pom.initPom()
        }
        create<MavenPublication>("release") {
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)
            artifactId = "ktPyString"
            version = "${project.version}"

            pom.initPom()
        }
    }
    repositories {
        maven {
            name = "bintray"
            val bintrayUsername = "chantsune"
            val bintrayRepoName = "ktPyString"
            val bintrayPackageName = "ktPyString"
            setUrl("https://api.bintray.com/content/$bintrayUsername/$bintrayRepoName/$bintrayPackageName/${project.version};publish=1;override=1")

            credentials {
                username = getBintrayUser()
                password = getBintrayKey()
            }
        }
    }
}
