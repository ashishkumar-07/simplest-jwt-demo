# Role-Based Access Control (RBAC) with JWT in Spring Boot

This guide provides a step-by-step approach to implementing Role-Based Access Control (RBAC) using JSON Web Tokens (JWT) in a Spring Boot application. The focus is on securing APIs with JWT rather than the default session-based authentication provided by Spring Security.

## Overview

JWT is a compact, URL-safe token format commonly used for securing APIs. In this guide, JWT is used to authenticate users and enforce role-based access control, ensuring that only authorized users can access specific endpoints.

## Dependencies

To implement JWT-based authentication and RBAC, the only security dependency which is needed is **spring-boot-starter-oauth2-resource-server**

## Security Configuration

The application is configured to:

- Expose the login endpoint publicly, allowing any user to access it without authentication.
- Secure all other endpoints, requiring users to be authenticated before access is granted.
- Enable method-level security to enforce access control at the method level.
- Customize JWT authentication by mapping roles to user authorities, enabling role-based access checks.

## JWT Token Generation

Upon successful login, a JWT token is generated that includes the user's details and roles. This token is then used to authenticate and authorize subsequent requests. The security of the JWT token is ensured by using a strong symmetric key for encoding.

## API Security

The API is secured with role-based access control:

- Only administrators have the privilege to register or delete users.
- Authenticated users can log in and view their own profiles.
- Administrators can view any user's profile.
- Users are allowed to update their own passwords.

## Service Layer

The service layer handles the core functionality of user registration, login, profile retrieval, and password updates. It ensures that passwords are securely hashed and that JWT tokens are correctly generated and validated.

## Database and Docker Support

The application utilizes a PostgreSQL database, which is easily set up using Docker Compose. This setup can be replaced with any preferred database configuration. On application startup, two test users with `ADMIN` and `USER` roles are created to facilitate testing and development.
