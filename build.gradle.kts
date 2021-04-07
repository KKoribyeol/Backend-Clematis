import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("jacoco")
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
    kotlin("plugin.allopen") version "1.4.21"
}

group = "com.dsm"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("mysql:mysql-connector-java")

    implementation("com.google.firebase:firebase-admin:6.8.1")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

    implementation("org.jacoco:org.jacoco.core:0.8.5")

    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("com.h2database:h2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
    }
    finalizedBy("jacocoTestCoverageVerification")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            enabled = true
            element = "CLASS"
            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
            excludes = mutableListOf(
                "com.dsm.clematis.global.*",
                "com.dsm.clematis.domain.account.controller.request.*",
                "com.dsm.clematis.domain.account.controller.response.*",
                "com.dsm.clematis.domain.account.domain.*",
                "com.dsm.clematis.domain.account.exception.*",
                "com.dsm.clematis.domain.affiliation.controller.request.*",
                "com.dsm.clematis.domain.affiliation.controller.response.*",
                "com.dsm.clematis.domain.affiliation.domain.*",
                "com.dsm.clematis.domain.affiliation.exception.*",
                "com.dsm.clematis.domain.group.controller.request.*",
                "com.dsm.clematis.domain.group.controller.response.*",
                "com.dsm.clematis.domain.group.domain.*",
                "com.dsm.clematis.domain.group.exception.*",
                "com.dsm.clematis.domain.project.controller.request.*",
                "com.dsm.clematis.domain.project.controller.response.*",
                "com.dsm.clematis.domain.project.domain.*",
                "com.dsm.clematis.domain.project.exception.*",
                "com.dsm.clematis.domain.target.controller.request.*",
                "com.dsm.clematis.domain.target.controller.response.*",
                "com.dsm.clematis.domain.target.domain.*",
                "com.dsm.clematis.domain.target.exception.*",
                "com.dsm.clematis.domain.template.controller.request.*",
                "com.dsm.clematis.domain.template.controller.response.*",
                "com.dsm.clematis.domain.template.domain.*",
                "com.dsm.clematis.domain.template.exception.*",
                "com.dsm.clematis.ClematisApplicationKt"
            )
        }
    }
}