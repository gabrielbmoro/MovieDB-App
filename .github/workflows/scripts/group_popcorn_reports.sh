#!/bin/bash

find . -type f -regex ".*/build/reports/popcornguineapig/errorReport.md" -exec cat {} +
