# <img src=".github/logo.png" alt="TextIt Logo" width="40"> TextIt - Secure Social Networking Platform

<div align="center">
  <img src="https://img.shields.io/badge/version-3.0.0-blue" alt="Version: 3.0.0">
  <img src="https://img.shields.io/badge/license-TCEL--1.0-brightgreen" alt="License: TCEL-1.0">
  <img src="https://img.shields.io/badge/Java-22%2B-007396?logo=java" alt="Java 22+">
  <img src="https://img.shields.io/badge/PostgreSQL-15%2B-336791?logo=postgresql" alt="PostgreSQL 15+">
  <img src="https://img.shields.io/badge/build-Maven-FF7F00?logo=apache-maven" alt="Maven">
  <img src="https://img.shields.io/badge/security-enterprise--grade-brightgreen" alt="Enterprise Security">
</div>

## 📝 Overview

**TextIt** is a next-generation, secure social networking platform developed by **TextIt Corporation**. Built with enterprise-grade security and a lightweight CLI interface, TextIt provides a fast and private social networking experience with a focus on real-time communication and content sharing, all while prioritizing user privacy and data protection.

<div align="center">
  <img src=".github/screenshots/app-preview.gif" alt="TextIt Preview" width="80%">
</div>

## ✨ Key Features

### 🔐 Advanced Security
- **End-to-End Encryption**: Military-grade encryption for all direct messages
- **OTP Authentication**: Secure login with email-based one-time passwords
- **Session Management**: Robust session handling for secure user access
- **Data Protection**: Secure storage and handling of user credentials

### 💬 Core Features
- **Real-time Messaging**: Instant direct messaging with online status
- **Post System**: Create and share text-based posts with followers
- **User Profiles**: Customizable profiles with bio and activity history
- **Follow System**: Follow other users and see their updates
- **Inbox Notifications**: Get alerts for new messages and interactions

### 🚀 Performance & Usability
- **Lightweight CLI**: Fast and responsive command-line interface
- **Low Resource Usage**: Optimized for performance on all systems
- **Keyboard Navigation**: Full keyboard control for power users
- **Themes**: Customizable color schemes for personalized experience

### 🛠️ Developer Friendly
- **Clean Architecture**: Well-organized codebase for easy contribution
- **Maven Build**: Simple dependency management and build process
- **Modular Design**: Independent components for maintainability
- **Comprehensive Logging**: Detailed logs for debugging and monitoring

## 🚀 Quick Start

### Prerequisites
- Java 22 or higher
- PostgreSQL 15+
- Maven 3.8+
- Git
- Internet connection (for email verification)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/TextItCorporation/TextItCLI.git
   cd TextItCLI
   ```

2. **Configure the application**
   ```bash
   cp config/application.example.yml config/application.yml
   # Edit the configuration file with your database credentials
   ```

3. **Build and run**
   ```bash
   mvn clean install
   java -jar target/TextItCLI-3.0.0.jar
   ```
   
4. **First-time setup**
   - Follow the on-screen prompts to create your admin account
   - Check your email for the verification code
   - Log in with your new credentials

## 🏗️ System Architecture

TextIt is built with a modular, layered architecture for maintainability and performance:

```
📦 TextItCLI
├── 📂 src/main/java/com/TextIt
│   ├── 📂 UI/            # Command-line interface components
│   ├── 📂 database/      # Database connection and operations
│   ├── 📂 inbox/         # Inbox and notification system
│   ├── 📂 model/         # Data models and entities
│   ├── 📂 security/      # Authentication and encryption
│   └── 📂 service/       # Business logic and features
│       ├── 📂 pages/     # Application screens
│       ├── 📂 session/   # User session management
│       └── 📂 user/      # User-related operations
├── 📂 src/main/resources # Configuration and resources
└── 📂 target/            # Compiled application
```

## 🔧 Technologies Used

### Core Technologies
- **Java 22**: Modern Java features and performance
- **PostgreSQL 15+**: Relational database for data persistence
- **JavaMail**: Email notifications and OTP delivery
- **Maven**: Dependency management and build automation
- **JDBC**: Database connectivity and operations

### Key Features
- **CLI Interface**: Fast, keyboard-driven user experience
- **Real-time Updates**: Event-driven architecture for live updates
- **Secure Storage**: Encrypted credentials and sensitive data
- **Modular Design**: Easy to extend with new features
- **Cross-Platform**: Runs anywhere Java is supported

## 📚 Documentation

- [User Guide](docs/USER_GUIDE.md) - Getting started and using TextIt
- [Developer Guide](docs/DEVELOPER_GUIDE.md) - Setup and contribution guidelines
- [Security Overview](docs/SECURITY.md) - Security features and best practices
- [CLI Reference](docs/CLI_REFERENCE.md) - Complete command reference
- [Troubleshooting](docs/TROUBLESHOOTING.md) - Common issues and solutions

## 🤝 Contributing

We welcome contributions from the community! Please read our [Contributing Guidelines](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the **TextIt Corporation Exclusive License (TCEL) v1.0** - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

- **Email**: support@TextItCorporation.com
- **GitHub Issues**: [Report Issues](https://github.com/TextItCorporation/TextItCLI/issues)
- **Documentation**: [Read the Docs](https://github.com/TextItCorporation/TextItCLI/tree/main/docs)
- **Community**: [Join our Discussions](https://github.com/TextItCorporation/TextItCLI/discussions)

## 🌐 Connect With Us

[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/TextItCorp)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/company/TextItCorporation)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/TextItCorporation)

---

<div align="center">
  <p>Made with ❤️ by <a href="https://github.com/TextItCorporation">TextIt Corporation</a></p>
  <p>© 2025 TextIt Corporation. All rights reserved. Version 3.0.0</p>
</div>

- [Contributing Guide](CONTRIBUTING.md) - How to contribute to the project
- [Governance](GOVERNANCE.md) - Project leadership and decision-making structure
- [Security Policy](SECURITY.md) - Reporting and handling security vulnerabilities
- [Changelog](CHANGELOG.md) - Record of all notable changes to the project

### For Contributors
- [Bug Report Template](.github/ISSUE_TEMPLATE/bug_report.md) - For reporting issues
- [Feature Request Template](.github/ISSUE_TEMPLATE/feature_request.md) - For suggesting enhancements
- [Pull Request Template](.github/PULL_REQUEST_TEMPLATE.md) - For submitting code changes
- [Code Owners](.github/CODEOWNERS) - Designated maintainers for different parts of the codebase

If you'd like to support TextIt financially, check out our [funding options](.github/FUNDING.yml).

---

<div align="center">
  <b>TextIt - Your Secure Social Media Experience</b>
</div>
