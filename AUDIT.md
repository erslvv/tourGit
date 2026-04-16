# AUDIT.md

Student ID: 230103128, 230103174, 230103215, 230103289

Repository Audit Score: 7.5/10

## Evaluation

### 1. README quality
The repository currently does not have a complete README file in the root. This makes the project harder to understand for a new developer or reviewer.

### 2. Folder structure
The main source code is organized under `src/main`, which follows a standard Maven and Spring Boot structure. However, the repository still does not fully match the required professional structure with separate `docs`, `tests`, and `assets` folders.

### 3. File naming consistency
Most core project files use consistent and standard names such as `pom.xml`, `Dockerfile`, `docker-compose.yml`, and `application.properties`. However, there is an unnecessary `.DS_Store` file in the repository root, which should be removed.

### 4. Presence of essential files
The repository already includes some essential files such as `.gitignore`, `Dockerfile`, `docker-compose.yml`, `pom.xml`, and Maven wrapper files. However, `README.md` and `LICENSE` are missing and must be added.

### 5. Commit history quality
The repository has a commit history, but it is still relatively small and can be improved with more descriptive commit messages for better professionalism and maintainability.

## Justification of Score
I gave this repository 7.5/10 because it already has a working technical foundation, standard Maven/Spring Boot structure, Docker support, and dependency management. However, it still needs documentation improvements, cleanup of unnecessary files, and missing repository-level files such as `README.md` and `LICENSE` to look fully professional and submission-ready.

## Planned Improvements
- Add a complete `README.md`
- Add ``
- Remove `.DS_Store`
- Add empty `docs`, `tests`, and `assets` directories if needed
- Improve repository professionalism and clarity