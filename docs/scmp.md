# Software Configuration Management Plan (SCMP)

**Project:** News Recommendation Microservice Platform
**Version:** 1.0
**Date:** 2025-12-25

## 1. Purpose & Scope
This document outlines the Software Configuration Management (SCM) activities for the News Recommendation Microservice Platform. It ensures the integrity of the software products throughout the project lifecycle by controlling changes, maintaining version consistency, and managing releases.

**Scope:**
- All source code (Java, Python, JavaScript)
- Documentation (Requirements, Architecture, API specs)
- Configuration files (Docker, Kubernetes, Application properties)
- Test scripts and data

## 2. Roles & Responsibilities
| Role | Responsibilities |
|------|------------------|
| **SCM Manager** | Oversees SCM process, approves baselines, manages releases. |
| **Developer** | Follows SCM procedures, creates branches, submits Pull Requests (PRs). |
| **Reviewer** | Reviews code and PRs, ensures quality and standard compliance. |
| **QA Engineer** | Verifies builds, conducts testing, reports defects. |

## 3. Configuration Item (CI) Identification
Configuration Items are entities that are placed under version control.

**Naming Conventions:**
- **Repositories:** `kebab-case` (e.g., `news-service`, `user-service`)
- **Documents:** `Title_vX.Y.md` (e.g., `Release_v1.0_Notes.md`)
- **Branches:** `type/description` (e.g., `feature/add-auth`, `fix/login-error`)
- **Tags:** `vX.Y.Z` (e.g., `v1.0.0`)

**CI Categories:**
- **Source Code:** `src/`, `pom.xml`, `requirements.txt`
- **Documentation:** `docs/`, `README.md`
- **Infrastructure:** `Dockerfile`, `docker-compose.yml`

## 4. Versioning Rules
The project follows **Semantic Versioning (SemVer) 2.0.0**: `MAJOR.MINOR.PATCH`

- **MAJOR**: Incompatible API changes.
- **MINOR**: Backward-compatible functionality additions.
- **PATCH**: Backward-compatible bug fixes.

**Example:** `v1.2.1`

## 5. Branching Model
We use a simplified **Git Flow** strategy:

- **`main`**: Production-ready code. Protected branch.
- **`develop`** (or `back`/`front`): Integration branch for ongoing development.
- **`feature/*`**: New features (e.g., `feature/ai-recommendation`). Merged into `develop` via PR.
- **`bugfix/*`**: Bug fixes (e.g., `bugfix/jwt-token`). Merged into `develop`.
- **`release/*`**: Preparation for a new production release.

## 6. Change Control Process
All changes must go through the Change Request (CR) process:

1.  **Request**: Issue created in GitHub (Bug/Feature Request).
2.  **Analysis**: Impact analysis and effort estimation.
3.  **Approval**: SCM Manager approves/rejects the CR.
4.  **Implementation**: Developer creates a branch and implements changes.
5.  **Verification**: Code review (PR) and testing.
6.  **Merge**: Approved changes merged into the main codebase.
7.  **Closure**: Issue closed.

## 7. Baseline Management
Baselines are established at key milestones to ensure stability.

- **Baseline 1 (BL1)**: Initial Setup (Repo structure, basic docs).
- **Baseline 2 (BL2)**: Prototype (Core services running).
- **Release v1.0**: First functional release.
- **Release v1.1**: Enhanced release with AI features.

Baselines are captured using **Git Tags** on the `main` branch.

## 8. Tools Used
- **Version Control System**: Git
- **Repository Hosting**: GitHub
- **CI/CD**: GitHub Actions (planned)
- **Containerization**: Docker, Docker Compose
- **Documentation**: Markdown
- **Project Management**: GitHub Projects/Issues
