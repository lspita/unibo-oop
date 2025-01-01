BASE_STATS="\n- Time to complete: N/A\n"
for exam in `find . -mindepth 1 -maxdepth 1 -type d -name "a*"`; do
    EXAM_RESULTS_FILE=${exam}/README.md
    echo -e "# Results for `basename ${exam}`\n${BASE_STATS}" > ${EXAM_RESULTS_FILE}
    for exercise in `find ${exam} -mindepth 1 -maxdepth 1 -type d -name "e*"`; do
        echo -e "## `basename ${exercise}`\n${BASE_STATS}" >> ${EXAM_RESULTS_FILE}
    done
done