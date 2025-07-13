# Changelog

All notable changes to TextIt will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Real-time notification system for likes and follows
- Enhanced security features with multi-factor authentication
- Improved database integration with optimized queries
- Thread-safe operations throughout the application
- Structured logging system

### Changed
- Updated security badge to reflect improved security posture
- Enhanced project architecture documentation
- Improved error handling and user feedback
- Optimized database connection management

## [1.5.0] - 2025-07-14

### Added
- Complete social features implementation:
  - Like tracking with real-time notifications
  - Follow system with notifications
  - Threaded notification system
- Advanced security features:
  - Multi-factor authentication
  - Enhanced password hashing
  - Improved encryption implementation
  - Thread-safe operations
- Modular architecture improvements:
  - Clean separation of concerns
  - Better package organization
  - Improved code maintainability

### Changed
- Updated project architecture to follow enterprise patterns
- Enhanced security implementation
- Improved database integration
- Better error handling and user feedback
- Optimized performance

### Fixed
- Various security vulnerabilities
- Database connection issues
- Race conditions in notification system
- Memory leaks in long-running processes

## [1.1.0] - 2025-07-07

### Added
- Implemented OTP-based email verification system
- Created custom exceptions for different validation scenarios
- Added PostgreSQL database integration for user data storage
- Implemented basic AES encryption capabilities
- Added SHA-256 password hashing
- Created framework for social features (follows, likes)

### Changed
- Refactored authentication system with interface-based design
- Improved input validation for usernames, passwords, emails, and phone numbers
- Enhanced project structure with better separation of concerns
- Updated all documentation to accurately reflect current project status
- Reorganized package structure for better maintainability

## [1.0.0] - 2023-11-15

### Added
- Initial release of TextIt
- User registration and authentication system
- Secure password management with validation for:
  - Upper and lowercase letters
  - Numbers
  - Special characters
  - Length between 8-16 characters
- User profile management
- Basic social media functionality:
  - Follow tracking
  - Like tracking
- Data persistence using a database
- Security features:
  - Password hashing
  - Data encryption

### Security
- Implemented secure authentication flow
- Added password strength requirements
- Implemented protection against common security vulnerabilities

## Types of changes
- `Added` for new features.
- `Changed` for changes in existing functionality.
- `Deprecated` for soon-to-be removed features.
- `Removed` for now removed features.
- `Fixed` for any bug fixes.
- `Security` in case of vulnerabilities.

## How to update
For detailed instructions on how to update from one version to another, please see the [upgrade guide](docs/UPGRADING.md).
