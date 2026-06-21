---
name: open-pr
description: Inspect current branch diff, generate a PR title and description from commits and changed files, then open the PR via gh pr create.
trigger: when the user asks to open a PR, create a PR, or submit a pull request
---

# Open PR

When invoked:

## Steps

### 1. Detect base branch
- Run `git branch --show-current` to confirm the current branch
- Try `git log origin/main..HEAD` and `git log origin/master..HEAD` — whichever succeeds is the base
- If neither works, ask the user to specify the base branch

### 2. Gather context
- Run `git log base..HEAD --oneline` to list commits on the branch
- Run `git log base..HEAD --oneline --reverse` (chronological order) for the PR body
- Run `git diff --stat base..HEAD` to see files changed and line counts
- Run `git remote get-url origin` to get the repo URL

### 3. Generate PR title
- Derive from the branch name: strip common prefixes (`feature/`, `fix/`, `chore/`, `refactor/`, `docs/`), then replace dashes/underscores with spaces
- If the branch name is not descriptive, infer the title from the dominant theme in commit messages
- Capitalize the first letter, keep it concise (≤ 72 chars)

### 4. Generate PR body

Format the body with these sections:

```markdown
## Description

One-sentence summary of what this PR does.

## Changes

- Bullet points summarizing each logical change group
- Group related commits together
- Mention files added/removed/modified and line counts

## Commits

- `abc1234` commit message
- `def5678` commit message
```

Guidelines:
- Read commit messages and diff stats to understand the scope
- For large diffs, read key files (new files, heavily modified files) to improve description accuracy
- Keep bullets concise and action-oriented
- Do not include secrets, tokens, or internal URLs
- If the diff is small (≤ 3 commits, ≤ 10 files), keep it brief

### 5. Present to user
- Show the proposed PR title and body
- Ask: "Ready to open this PR?" — let the user edit before proceeding
- Do NOT open the PR unless the user confirms

### 6. Open the PR
- Run `gh pr create --title "TITLE" --body "BODY"` from the repo root
- If `gh` is not authenticated, guide the user to run `gh auth login`
- Print the resulting PR URL
- Do NOT add `--web` flag — use headless creation
