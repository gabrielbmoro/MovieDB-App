---
name: update-tech-stack-readme
description: Sync the Tech Stack table in README.md and AGENTS.md from src/gradle/libs.versions.toml version catalog
license: MIT
compatibility: opencode
metadata:
  audience: developers
  workflow: documentation
---

## What I do

Updates the **Tech Stack** section in both `README.md` and `AGENTS.md` to reflect the actual library versions declared in `src/gradle/libs.versions.toml`.

### Steps

1. **Read the version catalog**
   - Open `src/gradle/libs.versions.toml` and parse all `[versions]` entries
   - Also parse `[libraries]` to map library module references to version refs

2. **Read target files**
   - Open `README.md` and locate the Tech Stack table (under `## Tech Stack`)
   - Open `AGENTS.md` and locate the Tech Stack table (under `## Tech Stack`)

3. **Update the tables**
   - For each row in the table:
     - Verify the library version matches what's in `libs.versions.toml`
     - If the version changed, update it in both `README.md` and `AGENTS.md`
   - If a new library was added to the catalog that should appear in the table:
     - Add a new row with its Category, Library name, and Version
   - If a library row references a version by its alias (e.g., `1.10.3 / 1.9.0` for Compose + Material3), match against the relevant version keys in the catalog

4. **Key version mappings** (catalog key → table entry)
   - `kotlin` → Kotlin version
   - `compose_plugin` or `compose-foundation` → Compose Multiplatform version
   - `material3` → Material3 version
   - `kotlinx-coroutines-core` or `coroutines` → Coroutines version
   - `ktor` → Ktor version
   - `coil` → Coil version
   - `room` → Room version
   - `sqlite` → SQLite bundled version
   - `koin` → Koin version
   - `koin-annotations` → Koin Annotations version
   - `koin-compiler-plugin` → Koin Compiler Plugin version
   - `kermit-version` → Kermit version
   - `rinku` → Rinku version
   - `buildkonfig` → BuildKonfig version
   - `popcorn_guineapig` → Popcorn Guineapig version
   - `detekt` → Detekt version
   - `lifecycle-viewmodel-compose-version` → Lifecycle ViewModel Compose version
   - `kotlinx-collections-immutable-version` → Collections Immutable version
   - `kotzilla` → Kotzilla version
   - `navigation-compose-version` → Navigation Compose version
   - `kover` (if present) or inferred from the build config → Kover version

5. **Verification**
   - After updating, re-read both files to confirm changes are correct
   - No verification build needed — this is a documentation-only change

### When NOT to use

- If only `README.md` has unrelated changes (run only when versions or tech stack actually changed)
- For updating Getting Started, Features, or other non-tech-stack sections
