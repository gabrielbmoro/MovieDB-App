---
name: update-architecture
description: Analyze and report on Clean Architecture module dependency compliance, and update AGENTS.md if the module structure changed.
trigger: when asked about architecture, after adding/removing modules, or when reviewing a PR that changes module dependencies
---

# Update Architecture

When invoked:

## Steps

### 1. Analyze current module structure
- Scan `src/settings.gradle.kts` for all included modules
- Map each module to its layer: `composeApp`, `data`, `domain`, `designsystem`, `platform`, `feature-*`, `androidApp`, `build-logic`
- Check `build.gradle.kts` files for project dependencies in each module

### 2. Verify dependency rules
- `:domain` — must NOT depend on any other project module
- `:data` — must ONLY depend on `:domain`
- `:designsystem` — must NOT depend on any other project module
- `:platform` — must NOT depend on any other project module
- `:feature-*` — must NOT depend on `:data` (can depend on `:domain`, `:designsystem`, `:platform`)
- Report any violations

### 3. Detect structural changes
- Compare current module list against the Module Structure section in `AGENTS.md`
- If new modules were added or removed, update `AGENTS.md` to reflect current state
- Also update the Architecture and Data Flow sections if they changed

### 4. Output
- List all modules and their layer assignments
- Report any dependency rule violations with file paths
- Report any `AGENTS.md` updates made (if structure changed)

## When to use

- After adding, renaming, or removing a module
- When reviewing a PR that changes module dependencies
- When asked about the project's architecture
