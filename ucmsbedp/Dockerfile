# syntax=docker/dockerfile:1.6

########### BUILD STAGE ###########
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# If you have a proxy, uncomment & set these (example):
# ARG HTTP_PROXY
# ARG HTTPS_PROXY
# ENV http_proxy=${HTTP_PROXY} https_proxy=${HTTPS_PROXY}

# Prefer IPv4 (sometimes helps on flaky DNS stacks)
ENV MAVEN_OPTS="-Djava.net.preferIPv4Stack=true"

# Optional: provide a settings.xml with mirrors/fallbacks (added below)
# If you add it, it will be picked up automatically
# COPY maven-settings.xml /root/.m2/settings.xml

# Copy only build descriptors first to leverage Docker layer cache
COPY pom.xml ./
# If you use Maven Wrapper, also copy it:
# COPY .mvn/ .mvn/
# COPY mvnw ./

# Warm up dependency cache (plugins + deps)
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -B -DskipTests dependency:resolve dependency:resolve-plugins

# Now copy sources and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -B -DskipTests package

########### RUNTIME STAGE ###########
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Create non-root user
RUN addgroup -S app && adduser -S app -G app
USER app

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
