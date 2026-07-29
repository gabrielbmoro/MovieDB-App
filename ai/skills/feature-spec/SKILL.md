---
name: feature-spec
description: Interactive interview to produce a feature specification for a feature described by the user.
disable-model-invocation: true
---

# Feature Spec

When invoked:

## Steps

### 1. Collect the feature description

Ask the user to describe the feature they want to build. This is `$ARGUMENTS` — the raw
input that will appear under **"Input"** in the spec header.

If the user already provided a description in the invoking message, confirm it and ask if
they want to add anything before proceeding.

**Completion criterion**: A feature description is recorded.

---

### 2. Create the spec file

Ask the user for a branch/issue identifier in the format `###-kebab-case-name`
(e.g., `042-add-dark-mode`). If the user doesn't have one, derive it from the feature
description.

Create the directory `docs/specs/` if it doesn't exist.

Write the spec file header to `docs/specs/<###-feature-name>.md`:

```markdown
# Feature Specification: [FEATURE NAME]

**Feature Branch**: `[###-feature-name]`

**Created**: [TODAY'S DATE]

**Status**: Draft

**Input**: User description: "$ARGUMENTS"
```

**Completion criterion**: Header is written to the file.

---

### 3. Interview: User Scenarios & Testing

Identify who the user is in 2-3 sentences (who has the problem, what they need).

Then elicit **prioritized user stories**. For each story:

1. **Title + Priority** — Brief title (P1, P2, P3...)
2. **Description** — Plain-language journey
3. **Why this priority** — The value it delivers
4. **Independent Test** — How this story alone delivers a viable MVP
5. **Acceptance Scenarios** — At least 1-2 in **Given/When/Then** format

Rules:
- P1 is the core MVP — "without this, the feature is useless."
- Each story must be independently testable/deployable/demoable.
- Help the user decompose broad descriptions into distinct slices.

After stories, ask about **edge cases**:
- Boundary conditions (empty state, max input, loading, timeout)
- Error states (no network, server error, invalid data)
- Concurrency (double-tap, rapid navigation, parallel actions)

Write the filled section to the spec file.

**Completion criterion**: At least one P1 story with acceptance scenarios, edge cases
section filled. User confirms stories are correct.

---

### 4. Interview: Functional Requirements

Derive FR-XXX entries from the user stories. Ask the user for any additional
requirements not captured by the stories.

Format: `- **FR-001**: System MUST [specific capability]`

Requirement types to probe for:
- Data persistence (what must be saved)
- Validation (what must be checked)
- Authorization (who can do what)
- Logging / analytics / error tracking
- Accessibility / localization

If the user doesn't know a detail, mark it: `[NEEDS CLARIFICATION: short description]`.

Write to the spec file.

**Completion criterion**: Functional requirements section filled. User confirms.

---

### 5. Interview: Key Entities

Ask: "Does this feature introduce new data or modify existing entities?"

If **yes**, for each entity capture:
- **Name** — What it represents
- **Key attributes** — Without implementation details (no types, no DB columns)
- **Relationships** — How it relates to other entities or existing domain models
- **Lifecycle** — Created when? Updated when? Deleted when?

If **no**, write "No new entities. This feature operates on existing data."

Write to the spec file.

**Completion criterion**: Key Entities section resolved (entities listed, or explicitly
noted as none).

---

### 6. Interview: Success Criteria

Elicit 3-5 measurable, technology-agnostic outcomes. Probe each category:

| Category | Example question |
|---|---|
| **Performance** | "How fast should [action] feel to the user?" |
| **Scale** | "How many [items/users] should this handle?" |
| **Usability** | "What's the success rate target for first-time users?" |
| **Reliability** | "What error rate is acceptable?" |
| **Business** | "What metric should improve after this ships?" |

Format: `- **SC-001**: [Measurable outcome]`

Write to the spec file.

**Completion criterion**: At least 3 success criteria. User confirms.

---

### 7. Interview: Assumptions

Document reasonable defaults the spec relies on but the user didn't explicitly state.
Probe:

- Target platform / environment assumptions
- Scope boundaries (what's explicitly out of scope for v1)
- Dependencies on existing systems or APIs
- User prerequisites (authentication, device capabilities, connectivity)

Write to the spec file.

**Completion criterion**: Assumptions section filled. User confirms.

---

### 8. Review & Finalize

Present the complete spec to the user. Offer to revisit any section.

Once the user is satisfied, update the status to `Review` and save the file.

**Completion criterion**: Spec saved to `docs/specs/<###-feature-name>.md` with all
sections filled, zero `[TODO]` placeholders, status is `Review`, user confirmed
satisfaction.

---

## Tips

- **Be relentless about priorities** — P1 stories are "can't ship without." If the user
  lists 5 P1s, push back: "Which one alone makes this feature viable?"
- **Given/When/Then format** — `Given [precondition], When [action], Then [observable
  outcome]`. The outcome must be something a tester can verify.
- **Accept vague answers** — mark as `[NEEDS CLARIFICATION]` rather than inventing
  details. The spec is a living document.
- **Keep it lean** — each section should fit on one screen. This isn't a design doc; it's
  a blueprint for implementation.
