#!/bin/bash

if [ ! $# -eq 1 ]; then
    echo "Invalid arguments: EXAMS_YEAR" 1>&2
    exit 1
fi

EXAMS_YEAR=$1
REMOTE_NAME=oop-exams${EXAMS_YEAR}
BRANCH_NAME=exams-${EXAMS_YEAR}
ORIGIN_EXAMS_ROOT=origin/exams-root
AUTO_COMMITS_PREFIX="[AUTO]"

# Create git branch
git remote add ${REMOTE_NAME} https://bitbucket.org/mviroli/oop${EXAMS_YEAR}-esami.git
git fetch ${REMOTE_NAME}
git checkout -b ${BRANCH_NAME} ${ORIGIN_EXAMS_ROOT}
git merge --allow-unrelated-histories ${REMOTE_NAME}/master -m "${AUTO_COMMITS_PREFIX} merged exam ${EXAMS_YEAR} sources"
git remote rm ${REMOTE_NAME}

# Setup
wget -P lib https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.1/junit-platform-console-standalone-1.9.1.jar
SETUP_COMMAND="!!"

# Create README
(echo "# OOP ${EXAMS_YEAR} exams";
echo "";
echo "## Setup";
echo "";
echo "\`\`\`sh";
echo "${SETUP_COMMAND}";
echo "\`\`\`";
echo "";
echo "## Compile & Run";
echo "";
echo "### JUnit exercises";
echo "";
echo "Only compile, run tests using VSCode integration";
echo "";
echo "\`\`\`sh";
echo "EXAM=a01";
echo "EXERCISE=e1";
echo "javac -cp lib/*.jar -d \${EXAM}/bin \${EXAM}/\${EXERCISE}/*.java"
echo "\`\`\`";
echo "";
echo "### Swing GUI exercises";
echo "";
echo "\`\`\`sh";
echo "EXAM=a01";
echo "EXERCISE=e2";
echo "MAINCLASS=Test"
echo "javac -d \${EXAM}/bin \${EXAM}/\${EXERCISE}/*.java"
echo "java -cp \${EXAM}/bin \${EXAM}.\${EXERCISE}.\${MAINCLASS}.java"
echo "\`\`\`";) > README.md

git add .
git commit -m "${AUTO_COMMITS_PREFIX} Updated README"

git branch
echo -n "Press enter to push:"
read 
git push -u origin ${BRANCH_NAME}