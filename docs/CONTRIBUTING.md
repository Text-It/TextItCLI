# Contributing to TextIt

<div align="center">
  <img src="https://img.shields.io/badge/Contributions-Welcome-brightgreen.svg" alt="Contributions Welcome">
  <img src="https://img.shields.io/badge/PRs-Welcome-blue.svg" alt="PRs Welcome">
  <img src="https://img.shields.io/badge/First--Timer-Friendly-6cc644.svg" alt="Good First Issue">
</div>

Thank you for your interest in contributing to TextIt! We appreciate your time and effort in helping us build a better platform. This guide will help you get started with contributing to our project.

## 📋 Table of Contents

- [Code of Conduct](#-code-of-conduct)
- [Getting Started](#-getting-started)
- [How to Contribute](#-how-to-contribute)
  - [Reporting Bugs](#-reporting-bugs)
  - [Suggesting Enhancements](#-suggesting-enhancements)
  - [Pull Requests](#-pull-requests)
- [Development Setup](#-development-setup)
- [Coding Standards](#-coding-standards)
- [Code Review Process](#-code-review-process)
- [Community](#-community)
- [License](#-license)

## 📜 Code of Conduct

By participating in this project, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md). We expect all contributors to uphold this code. Please report any unacceptable behavior to [conduct@TextItCorporation.com](mailto:conduct@TextItCorporation.com).

## 🚀 Getting Started

1. **Fork** the repository on GitHub
2. **Clone** your fork locally
   ```bash
   git clone https://github.com/your-username/TextItCLI.git
   cd TextItCLI
   ```
3. **Set up** the development environment
   ```bash
   # Ensure you have Java 22+ and Maven installed
   java -version
   mvn -v
   
   # Install dependencies
   mvn clean install
   ```
4. **Create a branch** for your changes
   ```bash
   git checkout -b type/scope/description
   # Example: git checkout -b feat/messaging/add-read-receipts
   ```
   
   Branch naming convention:
   - `feat/`: New features
   - `fix/`: Bug fixes
   - `docs/`: Documentation changes
   - `refactor/`: Code changes that neither fix bugs nor add features
   - `test/`: Adding missing tests or correcting existing tests

## 💡 How to Contribute

### 🐛 Reporting Bugs

Bugs are tracked as [GitHub issues](https://github.com/TextItCorporation/TextItCLI/issues). Before submitting a bug report:

1. **Search existing issues** to avoid duplicates
2. **Check the documentation** to ensure it's not a configuration issue
3. **Provide detailed information**:
   - TextIt version (run `java -jar target/TextItCLI-3.0.0.jar --version`)
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots or logs if applicable

1. **Search existing issues** to avoid duplicates
2. **Check the documentation** and [FAQ](#) for known issues
3. **Update to the latest version** to ensure the bug hasn't been fixed

#### Bug Report Template

```markdown
## Description
[Clear and concise description of the bug]

## Steps to Reproduce
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

## Expected Behavior
[What you expected to happen]

## Actual Behavior
[What actually happened]

## Environment
- TextIt Version: [e.g., 2.0.0]
- Java Version: [e.g., Java 17]
- OS: [e.g., Windows 11, macOS 12.6, Ubuntu 22.04]
- Database: [e.g., PostgreSQL 15]

## Additional Context
[Screenshots, logs, or any other relevant information]
```

### ✨ Suggesting Enhancements

We welcome suggestions for new features and improvements. Before submitting an enhancement:

1. **Search existing suggestions** to avoid duplicates
2. **Check the roadmap** in our [project board](#)
3. **Be specific** about your use case

#### Enhancement Template

```markdown
## Problem
[The problem this enhancement solves]

## Proposed Solution
[Your proposed solution]

## Alternatives Considered
[Any alternative solutions]

## Additional Context
[Any additional information]
```

### 🔄 Pull Requests

1. **Keep PRs focused** - One feature/bugfix per PR
2. **Update documentation** - Include relevant documentation updates
3. **Write tests** - Include unit and integration tests
4. **Follow coding standards** - See [Coding Standards](#-coding-standards)
5. **Update CHANGELOG.md** - Document your changes

#### PR Template

```markdown
## Description
[Description of changes]

## Related Issues
Fixes #[issue-number]

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update
- [ ] CI/CD update

## Checklist
- [ ] Code follows the style guidelines
- [ ] Tests have been added/updated
- [ ] Documentation has been updated
- [ ] CHANGELOG.md has been updated
```

## 🛠 Development Setup

### Prerequisites

- Java 22 (OpenJDK or Oracle JDK)
- Maven 3.8+
- PostgreSQL 15+
- Git 2.30+

### Local Development

1. **Database Setup**
   ```bash
   # Create a new PostgreSQL database
   createdb textit_cli
   
   # Configure database connection in src/main/resources/application.properties
   ```

2. **Running Tests**
   ```bash
   # Run all tests
   mvn test
   
   # Run a specific test class
   mvn test -Dtest=UserServiceTest
   ```
   
3. **Building the Project**
   ```bash
   # Create an executable JAR
   mvn clean package
   
   # Run the application
   java -jar target/TextItCLI-3.0.0.jar
   ```

## 📏 Coding Standards

### Java
- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Keep methods small and focused (max 30 lines)
- Add Javadoc for public APIs
- Write unit tests for new features

### Git
- Write clear, concise commit messages
- Use [Conventional Commits](https://www.conventionalcommits.org/)
- Keep commits atomic and focused
- Rebase your feature branch before submitting a PR

### Security
- Never commit secrets or credentials
- Validate all user inputs
- Use parameterized queries for database operations
- Follow the principle of least privilege

## 🔍 Code Review Process

1. **Automated Checks** - CI/CD pipeline runs tests and checks
2. **Initial Review** - A maintainer will review within 2 business days
3. **Address Feedback** - Update your PR with requested changes
4. **Approval** - After approval, your changes will be squashed and merged

### Review Guidelines
- Keep PRs focused and small (300-500 lines max)
- Include tests for new features and bug fixes
- Update documentation for any API changes
- Ensure all tests pass before requesting review
4. **Release** - Changes will be included in the next release

## 🌟 Community

- **Discord**: [Join our community](https://discord.gg/TextIt)
- **Twitter**: [@TextItCorp](https://twitter.com/TextItCorp)
- **Email**: [dev@TextItCorporation.com](mailto:dev@TextItCorporation.com)

## 📄 License

By contributing to TextIt, you agree that your contributions will be licensed under the [TCEL-1.0 License](LICENSE).

## 🤝 Community

- Join our [Discord server](https://discord.gg/textit) for real-time discussions
- Follow us on [Twitter @TextItCorp](https://twitter.com/TextItCorp) for updates
- Read our [blog](https://blog.textitcorp.com) for development insights
* Use proper exception handling
* Implement thread-safe operations
* Follow security guidelines
* Use meaningful variable and method names
* Include proper Javadoc documentation
* Follow package organization conventions
* Implement proper resource cleanup
* Use constants for magic numbers
* Follow SOLID principles
* Implement proper error handling
* Use 4 spaces for indentation
* Use camelCase for variables and methods
* Use PascalCase for class names
* Use UPPER_CASE for constants
* Place opening braces on the same line as the declaration
* Document all public methods and classes with Javadoc comments
* Keep methods small and focused on a single task
* Follow the principle of "Clean Code" as described by Robert C. Martin

### Current Development Focus

As TextIt is in early development, we are particularly interested in contributions in these areas:

* Improving the authentication system (completing login functionality)
* Enhancing security features (better password hashing, secure encryption)
* Developing the social features (follow system, like system)
* Optimizing database operations
* Adding comprehensive error handling
* Implementing proper logging instead of System.out.println
* Adding unit and integration tests

Please refer to our [task list](tasks.md) for specific items that need attention.

## Additional Notes

### Issue and Pull Request Labels

This section lists the labels we use to help us track and manage issues and pull requests.

* `bug` - Issues that are bugs
* `documentation` - Issues or PRs related to documentation
* `duplicate` - Issues that are duplicates of other issues
* `enhancement` - Issues that are feature requests
* `good first issue` - Good for newcomers
* `help wanted` - Extra attention is needed
* `invalid` - Issues that are invalid or non-reproducible
* `question` - Issues that are questions
* `wontfix` - Issues that will not be fixed

## Thank You!

Your contributions to open source, large or small, make great projects like TextIt possible. Thank you for taking the time to contribute.
