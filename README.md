# <img src=".github/logo.png" alt="TextIt Logo" width="40"> TextIt - Secure Social Networking Platform

<div align="center">
  <img src="https://img.shields.io/badge/version-2.0.0-blue" alt="Version: 2.0.0">
  <img src="https://img.shields.io/badge/license-TCEL--1.0-brightgreen" alt="License: TCEL-1.0">
  <img src="https://img.shields.io/badge/Java-17%2B-007396?logo=java" alt="Java 17+">
  <img src="https://img.shields.io/badge/PostgreSQL-13%2B-336791?logo=postgresql" alt="PostgreSQL 13+">
  <img src="https://img.shields.io/badge/build-Maven-FF7F00?logo=apache-maven" alt="Maven">
  <img src="https://img.shields.io/badge/security-enterprise--grade-brightgreen" alt="Enterprise Security">
</div>

## 📝 Overview

**TextIt** is a next-generation, secure social networking platform developed by **TextIt Corporation**. Built with enterprise-grade security and scalability in mind, TextIt provides a robust foundation for modern social interactions while prioritizing user privacy and data protection.

<div align="center">
  <img src=".github/screenshots/app-preview.gif" alt="TextIt Preview" width="80%">
</div>

## ✨ Key Features

### 🔐 Advanced Security
- **End-to-End Encryption**: Military-grade encryption for all communications
- **Multi-Factor Authentication**: Secure login with OTP, email, and biometric verification
- **Data Protection**: Compliance with GDPR, CCPA, and other privacy regulations
- **Secure Authentication**: Role-based access control (RBAC) implementation

### 🌐 Social Networking
- **Real-time Messaging**: Instant messaging with read receipts and typing indicators
- **Content Sharing**: Share text, images, and media with privacy controls
- **Activity Feed**: Personalized content feed with smart filtering
- **Groups & Communities**: Create and manage interest-based communities

### 🛠️ Developer Friendly
- **RESTful API**: Comprehensive API documentation
- **Modular Architecture**: Easy to extend and customize
- **CI/CD Ready**: GitHub Actions for automated testing and deployment
- **Containerized**: Docker support for easy deployment

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- PostgreSQL 13+
- Maven 3.8+
- Git

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
   java -jar target/TextItCLI-2.0.0.jar
   ```

## 🏗️ System Architecture

TextIt follows a clean architecture with clear separation of concerns:

```
📦 TextItCLI
├── 📂 src/main/java/com/TextIt
│   ├── 📂 api/            # REST API Controllers
│   ├── 📂 config/         # Application configuration
│   ├── 📂 core/           # Business logic
│   │   ├── 📂 auth/       # Authentication & Authorization
│   │   ├── 📂 user/       # User management
│   │   └── 📂 social/     # Social features
│   ├── 📂 data/           # Data access layer
│   │   ├── 📂 entities/   # JPA entities
│   │   ├── 📂 repositories/# Data repositories
│   │   └── 📂 migrations/ # Database migrations
│   ├── 📂 exception/      # Exception handling
│   └── 📂 util/           # Utility classes
├── 📂 src/main/resources  # Resources and configuration
└── 📂 src/test/           # Test suites
```

## 🔧 Technologies Used

### Backend
- **Java 17**: Core programming language
- **Spring Boot 3.0**: Application framework
- **Spring Security**: Authentication and authorization
- **JPA/Hibernate**: Database ORM
- **PostgreSQL**: Primary database
- **Redis**: Caching and real-time features
- **JWT**: Secure token-based authentication

### Development Tools
- **Maven**: Build automation
- **Docker**: Containerization
- **GitHub Actions**: CI/CD pipeline
- **Swagger/OpenAPI**: API documentation
- **Lombok**: Boilerplate reduction

## 📚 Documentation

- [API Documentation](https://docs.textitcorp.com/api) - Complete API reference
- [Developer Guide](docs/DEVELOPER_GUIDE.md) - Setup and contribution guidelines
- [Security Overview](docs/SECURITY.md) - Security features and best practices
- [Deployment Guide](docs/DEPLOYMENT.md) - Production deployment instructions

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
- **Phone**: +91 99999-88888
- **Website**: [www.TextITCorporation.com](https://www.TextITCorporation.com)
- **Address**: 123 Tech Park, Near Sola Road, S.G. Highway, Ahmedabad, Gujarat 380061, India

## 🌐 Connect With Us

[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/TextItCorp)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/company/TextItCorporation)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/TextItCorporation)

---

<div align="center">
  <p>Made with ❤️ by <a href="https://www.TextITCorporation.com">TextIt Corporation</a></p>
  <p>© 2025 TextIt Corporation. All rights reserved.</p>
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
