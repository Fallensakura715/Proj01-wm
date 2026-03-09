# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project structure

- This is a Maven multi-module Java 21 / Spring Boot 3 project.
- Root `pom.xml` aggregates three modules:
  - `P01-server`: the runnable backend application
  - `P01-common`: shared constants, exceptions, request context, result wrappers, config properties, and utilities
  - `P01-pojo`: shared `entity`, `dto`, and `vo` classes
- `P01-server/src/main/resources/mapper` contains the MyBatis XML for custom SQL.
- `proj01.sql` is the schema/bootstrap SQL for the local MySQL database.
- `src/main/java/CodeGenerator.java` is a MyBatis-Plus code generator that writes generated code into `P01-pojo` and `P01-server`; it is not part of the runtime app.

## Common commands

- Build all modules:
  - `mvn clean package`
- Run all tests:
  - `mvn test`
- Run server-module tests and build sibling modules as needed:
  - `mvn -pl P01-server -am test`
- Run a single test class:
  - `mvn -pl P01-server -am -Dtest=RedisTest test`
- Run a single test method:
  - `mvn -pl P01-server -am -Dtest=RedisTest#testString test`
- Start the Spring Boot app:
  - `mvn -pl P01-server -am spring-boot:run`
- Package only the server module and required local modules:
  - `mvn -pl P01-server -am package`
- There is no dedicated lint / formatter / Checkstyle / SpotBugs task configured in the Maven POMs.

## Runtime and test assumptions

- Main runtime config is in `P01-server/src/main/resources/application.yaml`.
- `application.properties` and `application-dev.yaml` are effectively placeholders and do not currently carry the main config.
- The app expects local infrastructure:
  - MySQL on `localhost:3306` with schema `proj01`
  - Redis on `localhost:6379`
- `proj01.sql` is the starting point for local database setup.
- User login depends on WeChat config, and image upload depends on Cloudflare R2-compatible S3 config.
- The existing tests are Spring Boot integration tests rather than isolated unit tests:
  - `RedisTest` talks to Redis
  - `R2Test` exercises `CommonService` upload behavior
- If local infra or external config is missing, `mvn test` is likely to fail.

## High-level architecture

### Application shape

- `P01-server` is the only runnable service. `P01ServerApplication` enables scheduling, caching, and transaction management.
- HTTP APIs are split by audience:
  - `controller.admin`: back-office/admin endpoints
  - `controller.user`: end-user/mobile endpoints
- Business logic lives in `service` / `service.impl`.
- Persistence uses MyBatis / MyBatis-Plus mappers, not Spring Data repositories.

### Request flow

- `WebMvcConfig` wires two JWT interceptors:
  - `/admin/**` uses the admin interceptor, excluding login and Swagger/OpenAPI routes
  - `/user/**` uses the user interceptor, excluding user login and shop-status routes
- Admin auth expects a `Bearer` token in the header named by `fallensakura.jwt.admin-token-name`.
- User auth expects the raw token in the header named by `fallensakura.jwt.user-token-name`.
- Both interceptors validate the JWT and then verify that the same token is still present in Redis.
- On success, the current principal ID is written into `BaseContext` (`ThreadLocal`) for downstream use.
- Controllers return shared `Result` / `PageResult` wrappers from `P01-common`.
- `GlobalExceptionHandler` normalizes common exceptions into the standard API response shape.

### Shared module responsibilities

- `P01-common` contains the cross-cutting pieces used throughout the app:
  - constants and exception types
  - `BaseContext` for request-scoped user/admin IDs
  - JWT utilities and config properties
  - WeChat config properties and HTTP helper utilities
  - common API response wrappers
- `P01-pojo` is the shared data-contract module:
  - `entity`: database/domain objects
  - `dto`: request/query input models
  - `vo`: response/view models

### Persistence model

- Mapper interfaces live in `P01-server/src/main/java/com/fallensakura/mapper`.
- Custom SQL lives in matching XML files under `P01-server/src/main/resources/mapper`.
- When changing data access behavior, check both the mapper interface and its XML file.
- `MyMetaObjectHandler` autofills audit fields such as `createTime`, `updateTime`, `createUser`, and `updateUser` using `BaseContext`.

## Major business areas

- Admin-side features include:
  - employee login/logout and employee management
  - category, dish, and setmeal management
  - shop status management
  - order handling and workspace/dashboard endpoints
- User-side features include:
  - WeChat-based login/logout
  - category, dish, and setmeal browsing
  - address management
  - shop status lookup
  - shopping cart scaffolding
- Order state transitions are centered in `OrderServiceImpl`.
- `OrderTask` contains scheduled jobs for order lifecycle automation, including auto-cancel for timed-out unpaid orders and auto-complete for delivered orders.

## Integrations and infrastructure

- Redis is used for login token/session state and other lightweight runtime flags.
- Cloudflare R2 is accessed through the AWS S3 SDK compatibility layer for image uploads.
- WebSocket support is exposed via `/ws/{sid}` and is used for server-to-client order notifications.
- User login exchanges a WeChat login code for an `openid`, then creates or loads a local user and issues a JWT.
- Elasticsearch is declared as a dependency in the root `pom.xml`, but there is no clear active Elasticsearch integration in the current codebase.

## Notes for future edits

- Auth changes usually involve controller login/logout code, JWT utilities/properties, interceptors, and Redis token storage together.
- For data model changes, review `proj01.sql`, the `entity`/`dto`/`vo` classes, mapper interfaces, and mapper XML together.
- Some classes appear to be partially generated or only lightly implemented, especially around shopping-cart-related code; confirm wiring before extending them.
- `application.yaml` currently contains real-looking credentials/secrets. Do not copy literal secret values into documentation or commits; refer to config keys only.
