Tasklist

This repository contains a project called Tasklist, created by Maksud9909. The purpose of this project is to help organize tasks for users. Users can create and manage tasks using this application.
Features

    User Authentication: Users can log in, create tasks, and update existing tasks.
    Role-Based Access Control: The application supports different user roles, such as ROLE_USER or ROLE_ADMIN.
    Task Management: Users can create tasks, and each task can have associated images.
    Swagger Documentation: Explore all available endpoints using the Swagger UI.



The main application communicates with the following components:

    Cache: Redis is used for caching.
    Database: PostgreSQL is the chosen database.
    Storage: MinIO is used for storing task-related images.

Environment Variables

To run this application, create a .env file in the root directory with the following environment variables:

    HOST: Host of the PostgreSQL database
    POSTGRES_USERNAME: Username for the PostgreSQL database
    POSTGRES_PASSWORD: Password for the PostgreSQL database
    POSTGRES_DATABASE: Name of the PostgreSQL database
    POSTGRES_SCHEMA: Name of the PostgreSQL schema
    REDIS_HOST: Host of the Redis instance
    REDIS_PASSWORD: Password for Redis
    JWT_SECRET: Secret string for JWT tokens
    MINIO_BUCKET: Name of the MinIO bucket
    MINIO_URL: URL of the MinIO instance
    MINIO_ACCESS_KEY: Access key for MinIO
    MINIO_SECRET_KEY: Secret key for MinIO
    SPRING_MAIL_HOST: Host of the mail server
    SPRING_MAIL_PORT: Port of the mail server
    SPRING_MAIL_USERNAME: Username of the mail server
    SPRING_MAIL_PASSWORD: Password of the mail server

You can use the example .env.example file with some predefined environments.
Getting Started

    Clone this repository.
    Set up the required environment variables in your .env file.
    Build and run the application.

Contributing

Feel free to contribute to this project by opening issues or submitting pull requests.

Please note that this README is a starting point, and you can enhance it further based on your project’s specifics. Good luck with your tasklist project! 🚀

