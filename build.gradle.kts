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
    implementation("io.javalin:javalin:6.1.4")
    implementation("org.commonmark:commonmark:0.22.0")
    implementation("org.commonmark:commonmark-ext-yaml-front-matter:0.22.0")
    implementation("org.slf4j:slf4j-simple:2.0.13")
}

application {
    mainClass.set("moe.das.blog.Main")
}


tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}
