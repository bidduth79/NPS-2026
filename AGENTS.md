# System Refactoring Rules

The following strict rules must be adhered to when refactoring this codebase, as explicitly requested by the user:

1. **One File at a Time (Step-by-Step):** Refactor files strictly one by one. Do not attempt to change multiple large components simultaneously. Complete the refactor of one file, verify it works, and then move to the next.
2. **Line Count Limit (< 200 Lines):** Every file in the system must be strictly under 200 lines. Large files must be modularized into smaller sub-components or utility files.
3. **Preserve UI (Zero Visual Breakage):** Absolutely NO UI can be broken or changed. The visual design, layout, and functionality must remain exactly the same as before the refactor.
4. **Create New Files for New Work:** Whenever adding new features, functionalities, or making significant updates, you MUST create new files rather than modifying or adding to existing ones. This ensures the codebase remains modular and strictly adheres to the file size limits.

**Process for execution:**
- Extract sub-components into new files.
- Hoist state/logic if needed.
- Run `compile_applet` to verify the build succeeds after every single file refactor.
