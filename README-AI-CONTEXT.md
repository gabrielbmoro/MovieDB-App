# AI Context Setup

This project uses a **centralized AI context structure**. One source of truth, read by all AI coding assistants.

## Why?

Before this, each AI assistant (Claude, Copilot, Cursor, Gemini, OpenCode) needed its own configuration file. Those files diverged, rules got stale, and maintaining them was error-prone.

Now there is **one** master file (`AGENTS.md`) and **one** shared `ai/` folder. Everything else is a lightweight pointer.

## How It Works

```
CLAUDE.md ──────────────┐
.cursorrules ───────────┤
.github/copilot-instructions.md ──┤
.gemini/context.md ─────┤
opencode.json (instructions) ──┤
                           │
                           ▼
                      AGENTS.md
                      (master initializer:
                       what the project is,
                       how to implement tasks,
                       build validation,
                       PR review checklist)
                           │
              ┌────────────┼────────────┐
              ▼            ▼            ▼
        ai/skills/   ai/instructions/   ai/module-graph.md
        (task type   (platform-specific  (dependency rules)
         workflows)   patterns)
```

## File Map

| File | Target | Purpose |
|---|---|---|
| `AGENTS.md` | All assistants | Master context: project overview, architecture, conventions, build commands, PR checklist |
| `ai/module-graph.md` | All assistants | Explicit module dependency graph (enforced by Popcorn Guineapig) |
| `ai/instructions/android.md` | All assistants | Android-specific patterns, entry points, theming, deep links |
| `ai/instructions/ios.md` | All assistants | iOS-specific patterns, entry points, Xcode integration |
| `ai/skills/` | All assistants | Task-type workflows (code-review, generate-unit-tests, update-architecture, update-tech-stack-readme) |
| `CLAUDE.md` | Claude (Claude Code, Claude in IDE) | Pointer → AGENTS.md |
| `.cursorrules` | Cursor | Pointer → AGENTS.md |
| `.github/copilot-instructions.md` | GitHub Copilot | Pointer → AGENTS.md |
| `.gemini/context.md` | Gemini Code Assist | Pointer → AGENTS.md |
| `opencode.json` | OpenCode | Instructions, MCP servers, skill paths |

## Maintenance

- **Only `AGENTS.md` and `ai/` files need maintenance.** The pointer files never change.
- When the architecture, conventions, or tech stack changes: update `AGENTS.md` and relevant `ai/` files.
- When a new skill workflow is needed: add a file to `ai/skills/`.
- When a new AI tool is adopted: create a pointer file (2 lines) pointing to `AGENTS.md`.

## Verification

Run the build validation commands listed in `AGENTS.md` to verify your changes don't break anything.
