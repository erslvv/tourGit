# AUDIT.md

Student ID: 230103128, 230103174, 230103215, 230103289

Repository Audit Score: 8/10

## Evaluation

### 1. README quality
The repository now has a README file, but originally it lacked clear project-level documentation. A good README is important because it helps other developers and reviewers quickly understand the purpose of the project, its technologies, and how to run it.

### 2. Folder structure
The backend follows a standard Maven and Spring Boot structure under src/main, which is correct and professional. The repository also contains a separate frontend folder, safetrip-front, which makes it clear that the project includes both backend and frontend parts. However, the repository can still be improved by keeping supporting materials in dedicated folders such as docs, tests, and assets.

### 3. File naming consistency
Most important files follow clear and standard naming conventions, such as pom.xml, Dockerfile, docker-compose.yml, README.md, and LICENSE. The main issue is the presence of an unnecessary .DS_Store file in the repository root, which should be removed because it is a system-generated file and does not belong in version control.

### 4. Presence of essential files
The repository includes important project files such as:
- .gitignore
- Dockerfile
- docker-compose.yml
- pom.xml
- README.md
- LICENSE
- AUDIT.md

This gives the repository a more complete and professional structure.

### 5. Commit history quality
The repository has a visible commit history, which is good for tracking progress. However, commit quality can still be improved further by making messages more descriptive and more clearly connected to the actual changes.

## Justification of Score
I gave this repository 8/10 because it already has a solid technical foundation, a recognizable backend structure, a separate frontend module, Docker-related files, and the required repository-level files. It looks much more professional after cleanup. The main remaining improvements are removing unnecessary files like .DS_Store, improving documentation even more, and keeping the structure consistently organized.

## Improvements Made
- Added AUDIT.md
- Added README.md
- Added LICENSE
- Improved overall repository professionalism
- Clarified project structure
- Identified unnecessary root-level files for cleanup