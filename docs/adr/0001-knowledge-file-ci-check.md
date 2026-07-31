# Knowledge file staleness check via Danger JS

The project has ~28 "knowledge files" (AGENTS.md, module READMEs, AI instructions, specs)
that serve as the agent memory bank. Keeping them in sync with code changes is manual
and error-prone. We decided to add a Danger JS CI check that warns when a PR's code
changes likely require updates to specific knowledge files.

We chose Danger JS over Danger Kotlin (unmaintained since 2021) and Danger Ruby (would
add an alien runtime to a Kotlin/Gradle stack). The check runs on every PR using
warn-level annotations — it never blocks merging, but surfaces documentation drift
before it accumulates.

Considered options: Danger Kotlin (last release 2021, effectively abandoned), Danger Ruby
(adds Ruby to a Kotlin-only stack, unfamiliar tooling), and a custom Gradle-based solution
(no PR comment integration, more code to maintain). Danger JS is the standard choice for
GitHub-hosted PR checks with excellent template/comment management out of the box.

The mapping rules are simple enough (~6 rules) that they live inline in the Dangerfile
rather than in a separate config file. If the rule set grows significantly, extracting
to a declarative format should be reconsidered.
