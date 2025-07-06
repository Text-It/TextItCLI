# Security Policy

*Last Updated: July 7, 2025*

## Supported Versions

The following versions of TextIt are currently being supported with security updates:

| Version | Supported          |
| ------- | ------------------ |
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

- Password validation requirements:
  - Uppercase letters
  - Lowercase letters
  - Numbers
  - Special characters (!, @, $, &, *)
  - Length between 8-16 characters
- Basic password hashing using SHA-256
- Email verification using one-time passwords (OTPs)

### Data Protection

- Basic AES encryption capabilities (currently using default ECB mode)
- Database validation for unique usernames, emails, and phone numbers
- Secure email delivery for OTPs using Gmail SMTP with TLS

### Planned Security Enhancements

- Improved password hashing with salt using bcrypt or PBKDF2
- Enhanced encryption using more secure modes (GCM instead of ECB)
- Proper key management for encryption keys
- Protection against common attacks like brute force and SQL injection
- Comprehensive input validation and sanitization

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
