plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.0-M7"

dependencies {
    val httpclientVersion = "5.4.1"             // http-client (http-core 버전과 호환성 유의)
    val httpcoreVersion = "5.3.1"               // http-core
    val commonsLang3Version = "3.17.0"          // commons-lang

    implementation("org.springframework.ai:spring-ai-starter-mcp-server-webmvc")

    // commons, utils
    implementation("org.apache.httpcomponents.client5:httpclient5:$httpclientVersion")
    implementation("org.apache.httpcomponents.core5:httpcore5:$httpcoreVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLang3Version")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
