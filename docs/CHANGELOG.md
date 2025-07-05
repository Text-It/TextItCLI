# Changelog

All notable changes to TextIt will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Future features in development

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
