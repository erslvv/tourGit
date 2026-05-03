# AUDIT.md

Student ID: 230103128, 230103174, 230103215, 230103289

Repository Audit Score: 8/10

## Evaluation

### 1. README quality
Originally, there was no project-level documentation in the repository. There is currently a README file, which is beneficial because better README files are useful for other developers or reviewers to quickly gain an understanding of a project, the tech stack involved, and how to execute it.

### 2. Folder structure
The backend implements a standard Maven and Spring Boot structure under src/main. The repository also has a separate frontend folder, safetrip-front which explicitly indicates that both frontend and backend parts are present in the project. The organization of the repository can be improved by placing supplementary content in separate folders, such as docs, tests, and assets.

### 3. File naming consistency
Most important files follow clear and standard naming conventions like a pom.xml, Dockerfile, docker-compose.yml, README.md and LICENSE. The main issue is the presence of an unnecessary .DS_Store file in the repository root that should be removed because it is a system generated file and does not belong in version control.

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
Our repository has a visible commit history, and it is good for tracking progress. But commit quality can still be improved further by making messages more descriptive and more clearly connected to the actual changes.

## Justification of Score
I gave this repository 8/10 because it already has a solid technical foundation, a recognizable backend structure, a separate frontend module, Docker-related files, and the required repository-level files. It looks much more professional after cleanup. The main remaining improvements are removing unnecessary files like .DS_Store, improving documentation even more, and keeping the structure consistently organized.

## Improvements Made
- Added AUDIT.md
- Added README.md
- Added LICENSE
- Improved overall repository professionalism
- Clarified project structure
- Identified unnecessary root-level files for cleanup