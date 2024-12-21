# OOP 2022 exams

## Setup

```sh
wget -c -P lib https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.1/junit-platform-console-standalone-1.9.1.jar
```

## Compile & Run

### JUnit exercises

Only compile, run tests using VSCode integration

```sh
EXAM=a01
EXERCISE=e1
javac -cp lib/*.jar -d ${EXAM}/bin ${EXAM}/${EXERCISE}/*.java
```

### Swing GUI exercises

```sh
EXAM=a01
EXERCISE=e2
MAINCLASS=Test
javac -d ${EXAM}/bin ${EXAM}/${EXERCISE}/*.java
java -cp ${EXAM}/bin ${EXAM}.${EXERCISE}.${MAINCLASS}.java
```

