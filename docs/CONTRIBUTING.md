# Contributing to TextIt

Thank you for considering contributing to TextIt! This document outlines the process for contributing to the project and helps to ensure a smooth collaboration experience for everyone involved.

*Last updated: July 14, 2025*

## Code of Conduct

By participating in this project, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md). Please read it before contributing.

## How Can I Contribute?

### Reporting Bugs

This section guides you through submitting a bug report for TextIt. Following these guidelines helps maintainers understand your report, reproduce the issue, and find related reports.

Before creating bug reports, please check [this list](#before-submitting-a-bug-report) as you might find out that you don't need to create one. When you are creating a bug report, please [include as many details as possible](#how-do-i-submit-a-good-bug-report).

#### Before Submitting A Bug Report

* **Check the [documentation](README.md)** for a list of common questions and problems.
* **Perform a [search](https://github.com/yourusername/textit/issues)** to see if the problem has already been reported. If it has and the issue is still open, add a comment to the existing issue instead of opening a new one.

#### How Do I Submit A Good Bug Report?

Bugs are tracked as [GitHub issues](https://github.com/yourusername/textit/issues). Create an issue and provide the following information:

* **Use a clear and descriptive title** for the issue to identify the problem.
* **Describe the exact steps which reproduce the problem** in as many details as possible.
* **Provide specific examples to demonstrate the steps**. Include links to files or GitHub projects, or copy/pasteable snippets, which you use in those examples.
* **Describe the behavior you observed after following the steps** and point out what exactly is the problem with that behavior.
* **Explain which behavior you expected to see instead and why.**
* **Include screenshots and animated GIFs** which show you following the described steps and clearly demonstrate the problem.
* **If the problem is related to performance or memory**, include a CPU profile capture with your report.
* **If the problem wasn't triggered by a specific action**, describe what you were doing before the problem happened and share more information using the guidelines below.
* **Specify the version of TextIt you're using (1.5.0)**
* **Specify your Java version (Java 8)**
* **Specify your PostgreSQL version if relevant**

### Suggesting Enhancements

This section guides you through submitting an enhancement suggestion for TextIt, including completely new features and minor improvements to existing functionality.

#### Before Submitting An Enhancement Suggestion

* **Check the [documentation](README.md)** to see if the enhancement has already been suggested.
* **Perform a [search](https://github.com/yourusername/textit/issues)** to see if the enhancement has already been suggested. If it has, add a comment to the existing issue instead of opening a new one.

#### How Do I Submit A Good Enhancement Suggestion?

Enhancement suggestions are tracked as [GitHub issues](https://github.com/yourusername/textit/issues). Create an issue and provide the following information:

* **Use a clear and descriptive title** for the issue to identify the suggestion.
* **Provide a step-by-step description of the suggested enhancement** in as many details as possible.
* **Provide specific examples to demonstrate the steps**. Include copy/pasteable snippets which you use in those examples.
* **Describe the current behavior** and **explain which behavior you expected to see instead** and why.
* **Include screenshots and animated GIFs** which help you demonstrate the steps or point out the part of TextIt which the suggestion is related to.
* **Explain why this enhancement would be useful** to most TextIt users.
* **List some other applications where this enhancement exists.**
* **Specify which version of TextIt you're using (1.5.0)**
* **Specify the name and version of the OS you're using.**
* **Specify your Java version (Java 8)**
* **Specify your PostgreSQL version if relevant**

### Pull Requests

The process described here has several goals:

- Maintain TextIt's quality
- Fix problems that are important to users
- Engage the community in working toward the best possible TextIt
- Enable a sustainable system for TextIt's maintainers to review contributions

Please follow these steps to have your contribution considered by the maintainers:

1. Follow all instructions in [the template](PULL_REQUEST_TEMPLATE.md)
2. Follow the [styleguides](#styleguides)
3. After you submit your pull request, verify that all [status checks](https://help.github.com/articles/about-status-checks/) are passing
4. Ensure your code follows our security guidelines:
   - No hardcoded credentials
   - Proper input validation
   - Secure database operations
   - Thread-safe operations
5. Include appropriate test cases for new features

While the prerequisites above must be satisfied prior to having your pull request reviewed, the reviewer(s) may ask you to complete additional design work, tests, or other changes before your pull request can be ultimately accepted.

## Styleguides

### Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* Reference issues and pull requests liberally after the first line
* Include security implications if relevant
* Reference any security fixes
* Follow semantic versioning guidelines
    * 🔒 `:lock:` when dealing with security
    * ⬆️ `:arrow_up:` when upgrading dependencies
    * ⬇️ `:arrow_down:` when downgrading dependencies
    * 👕 `:shirt:` when removing linter warnings

### Java Styleguide

* Follow Java 8 best practices
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
