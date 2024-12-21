#!/bin/bash

if (( $# < 1 || $# > 3 )); then
    echo "Invalid number of arguments" 1>&2
    exit 1
fi

EXAMS_YEAR=$1
EXAMS_BRANCH=${2:-"master"}
JUNIT_SOURCE=${3:-"https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.1/junit-platform-console-standalone-1.9.1.jar"}
REMOTE_NAME=oop-exams${EXAMS_YEAR}
BRANCH_NAME=exams-${EXAMS_YEAR}
AUTO_COMMITS_PREFIX="[AUTO]"

# Create git branch
git remote add ${REMOTE_NAME} https://bitbucket.org/mviroli/oop${EXAMS_YEAR}-esami.git
git fetch ${REMOTE_NAME}
git checkout -b ${BRANCH_NAME}
git merge --allow-unrelated-histories ${REMOTE_NAME}/${EXAMS_BRANCH} -m "${AUTO_COMMITS_PREFIX} merged exam ${EXAMS_YEAR} sources"
git remote rm ${REMOTE_NAME}

# Setup
SETUP_COMMAND="wget -c -P lib ${JUNIT_SOURCE}"
eval "${SETUP_COMMAND}"

# Create README
echo "# OOP ${EXAMS_YEAR} exams

## Setup

\`\`\`sh
${SETUP_COMMAND}
\`\`\`

## Compile & Run

### JUnit exercises

Only compile, run tests using VSCode integration

\`\`\`sh
EXAM=a01
EXERCISE=e1
javac -cp lib/*.jar -d \${EXAM}/bin \${EXAM}/\${EXERCISE}/*.java
\`\`\`

### Swing GUI exercises

\`\`\`sh
EXAM=a01
EXERCISE=e2
MAINCLASS=Test
javac -d \${EXAM}/bin \${EXAM}/\${EXERCISE}/*.java
java -cp \${EXAM}/bin \${EXAM}.\${EXERCISE}.\${MAINCLASS}.java
\`\`\`
" > README.md

# Remove this script
rm $(basename "$0")

git add .
git commit -m "${AUTO_COMMITS_PREFIX} initial commit"

git branch
echo -n "Press enter to push:"
read 
git push -u origin ${BRANCH_NAME}