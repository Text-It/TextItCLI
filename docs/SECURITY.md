# Security Policy

*Last Updated: July 14, 2025*

## Supported Versions

The following versions of TextIt are currently being supported with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.5.x   | :white_check_mark: |
| 1.1.x   | :white_check_mark: |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

> **Note**: TextIt is in early development, and security features are still being enhanced.

## Reporting a Vulnerability

The TextIt team takes security bugs seriously. We appreciate your efforts to responsibly disclose your findings and will make every effort to acknowledge your contributions.

To report a security issue, please follow these steps:

1. **DO NOT** disclose the vulnerability publicly.
2. Email your findings to [security@textit.example.com](mailto:security@textit.example.com). If possible, encrypt your message with our PGP key.
3. Allow time for the team to respond and address the vulnerability.
4. After the vulnerability has been addressed, we will work with you to publicly disclose the vulnerability.

## What to Include in Your Report

When reporting a vulnerability, please include as much information as possible, including:

- A description of the vulnerability
- Steps to reproduce the issue
- Potential impact of the vulnerability
- Any potential mitigations
- If applicable, any proof-of-concept code

## Our Commitment

The TextIt team is committed to:

- Responding to your report within 48 hours, acknowledging receipt
- Providing an estimated timeline for a fix
- Notifying you when the vulnerability has been fixed
- Crediting you in the security advisory unless you request otherwise

## Current Security Measures in TextIt

TextIt is being built with security in mind. Our current implementation includes:

### Authentication Security

- Multi-factor authentication:
  - Email verification with OTPs
  - Phone number verification
  - Username/password combination
- Password security:
  - SHA-256 hashing with salt
  - Complexity requirements:
    - 8-16 characters
    - Uppercase and lowercase letters
    - Numbers
    - Special characters
- Thread-safe authentication operations
- Rate limiting for login attempts
- Secure session management

### Data Protection

- Advanced encryption:
  - AES-128 encryption
  - Thread-safe encryption operations
- Secure data storage:
  - PostgreSQL database with proper indexing
  - Transaction support
  - Optimized queries
- Secure communication:
  - TLS for email delivery
  - Thread-safe database operations
- Input validation:
  - Custom exceptions for validation
  - Comprehensive input sanitization
  - Protection against SQL injection

### Current Security Enhancements

- Security Features Implemented:
  - Thread-safe operations throughout the application
  - Structured exception handling
  - Secure session management
  - Rate limiting
  - Input validation and sanitization

### Future Security Enhancements

- Advanced encryption:
  - GCM mode support
  - Secure key management
- Authentication:
  - TOTP support
  - Biometric authentication
- Security Monitoring:
  - Audit logging
  - Security event tracking
  - Regular security audits

## Security Updates

Security updates will be announced through:

1. GitHub security advisories
2. Release notes
3. The official TextIt communication channels

## Security Best Practices for Users

We recommend that users of TextIt follow these security best practices:

1. Use strong, unique passwords
2. Keep your client software updated to the latest version
3. Be cautious about the information you share through the platform
4. Report any suspicious activity immediately

Thank you for helping keep TextIt and our users safe!
