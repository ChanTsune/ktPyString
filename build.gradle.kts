import org.gradle.api.publish.maven.MavenPom


group = "dev.tsune"
version = "0.1.0"

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.8.21"

    // Apply plugin for document generation
    id("org.jetbrains.dokka") version "1.8.20"

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

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
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
