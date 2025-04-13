plugins {
    id("java")
    id("application")
}

group = "moe.das"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.6.0")
    implementation("org.commonmark:commonmark:0.24.0")
    implementation("org.commonmark:commonmark-ext-yaml-front-matter:0.24.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

application {
    mainClass.set("moe.das.blog.Main")
}


tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}
