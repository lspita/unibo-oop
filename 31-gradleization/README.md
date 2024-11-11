# Gradle-ization of an existing project

Transform a "plain" Java project into a Gradle Java project.
Follow the steps below:

1. Adhere to the *convention*, in order to avoid as much configuration as possibile, by moving all sources in `src/main/java/`
2. Set up the Gradle wrapper, to avoid custom Gradle installation and select a specific Gradle version.
    1. Download a copy of the wrapper, for instance from https://github.com/DanySK/gradle-wrapper/archive/refs/heads/master.zip
    2. decompress the archive
    3. move the relevant files into the project root:
        - `gradlew`
        - `gradlew.bat`
        - the `gradle/wrapper` folder with the `gradle-wrapper.jar` and the `gradle-wrapper.properties` files
    4. On Linux and MacOsX, make sure that `gradlew` is an executable script by running `chmod +x gradlew`
3. Set a project name
    - Create a file named `settings.gradle.kts`
    - The file content should be `rootProject.name = "gradleized-project"`
4. The project is now a Gradle project, verify that the build system is enabled:
    - `./gradlew tasks --all` (on Windows, `gradlew.bat tasks`)
5. Configure Gradle for Java
    - Create a file named `build.gradle.kts`
    - The file content should be `plugins { java }`
6. Verify that the project can compile via Gradle
    - `./gradlew compileJava`
    - Check the contents of the `build` folder, find where Gradle compiles
7. Use `java` to run `WorkWithArrays`
