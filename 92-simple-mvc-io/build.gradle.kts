plugins {
    application
    java
    id("org.danilopianini.gradle-java-qa") version "1.75.0"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("it.unibo.mvc.SimpleGUIWithFileChooser")
}

tasks.javadoc {
    isFailOnError = false
}
