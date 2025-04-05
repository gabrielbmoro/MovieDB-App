#!/bin/bash
./gradlew popcorn -PerrorReportEnabled
EXIT_CODE=$?

find . -type f -regex ".*/build/reports/popcornguineapig/errorReport.md" -exec cat {} +

if [ "$EXIT_CODE" == 1 ]; then
    exit $EXIT_CODE
fi