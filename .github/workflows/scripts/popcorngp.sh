#!/bin/bash
./src/gradlew popcorn -PerrorReportEnabled
EXIT_CODE=$?

find . -type f -regex ".*/build/reports/popcornguineapig/errorReport.md" -exec cat {} + >> $COMMENT_BODY

GITHUB_TOKEN = ${{ secrets.GITHUB_TOKEN }}
REPO = ${{ github.repository }}
PR_NUMBER = ${{ github.event.pull_request.number }}
API_URL="https://api.github.com"
AUTH_HEADER="Authorization: Bearer $GITHUB_TOKEN"
ACCEPT_HEADER="Accept: application/vnd.github+json"
USER_AGENT="User-Agent: my-bot"

# Find existing comment
comments=$(curl -s -H "$AUTH_HEADER" -H "$ACCEPT_HEADER" -H "$USER_AGENT" \
"$API_URL/repos/$REPO/issues/$PR_NUMBER/comments")

# Extract the comment ID if it contains our marker
comment_id=$(echo "$comments" | jq -r '.[] | select(.user.login=="github-actions[bot]") | select(.body | contains("Module analysis")) | .id')

if [ -n "$comment_id" ]; then
echo "Updating existing comment (ID: $comment_id)..."
curl -s -X PATCH -H "$AUTH_HEADER" -H "$ACCEPT_HEADER" -H "$USER_AGENT" \
    -d "$(jq -nc --arg body "$COMMENT_BODY" '{body: $body}')" \
    "$API_URL/repos/$REPO/issues/comments/$comment_id"
else
echo "Creating new comment..."
curl -s -X POST -H "$AUTH_HEADER" -H "$ACCEPT_HEADER" -H "$USER_AGENT" \
    -d "$(jq -nc --arg body "$COMMENT_BODY" '{body: $body}')" \
    "$API_URL/repos/$REPO/issues/$PR_NUMBER/comments"
fi

# Fail the error
if [ "$EXIT_CODE" == 1 ]; then
    exit $EXIT_CODE
fi