import org.gradle.api.publish.maven.MavenPom
import org.jetbrains.dokka.gradle.DokkaTask


group = "dev.tsune"
version = "1.0.0-beta1"

plugins {
    kotlin("multiplatform") version "1.4.0"

    // Apply plugin for document generation
    id("org.jetbrains.dokka") version "0.9.18"


    id("com.jfrog.bintray") version "1.8.4"

    // Apply the java-library plugin for API and implementation separation.
    id("java-library")

    // Apply library publish plugin
    id("maven-publish")
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
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

//sources
val sourcesJar by tasks.creating(Jar::class) {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

val dokka by tasks.getting(DokkaTask::class){
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

// Create dokka Jar task from dokka task output
val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
    from(tasks.dokka)
}

//publications
val snapshot = "snapshot"
val release = "release"

publishing {
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
        create<MavenPublication>(snapshot) {
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)
            artifactId = "ktPyString"
            version = "${project.version}-SNAPSHOT"

            pom.initPom()
        }
        create<MavenPublication>(release) {
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)
            artifactId = "ktPyString"
            version = "${project.version}"

            pom.initPom()
        }
    }
}


bintray {
    user = findProperty("bintray_user") as? String
    key = findProperty("bintray_key") as? String

    val isRelease = findProperty("release") == "true"

    publish = isRelease
    override = false

    setPublications(if (isRelease) release else snapshot)

//    dryRun = true

    with(pkg) {
        repo = "ktPyString"
        name = "ktPyString"
        setLicenses("MIT")
        setLabels("kotlin")
        websiteUrl = "https://github.com/ChanTsune/ktPyString"
        issueTrackerUrl = "https://github.com/ChanTsune/ktPyString/issues"
        vcsUrl = "https://github.com/ChanTsune/ktPyString.git"

        with(version) {
            name = project.version.toString()
            vcsTag = project.version.toString()
        }
    }
}
