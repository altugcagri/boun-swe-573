[![CircleCI](https://circleci.com/gh/altugcagri/boun-swe-573/tree/dev.svg?style=svg&circle-token=8d8c2fc0288c5c84c31d65acd89cb4fe273a3b69)](https://circleci.com/gh/altugcagri/boun-swe-573/tree/dev)

[![Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=altugcagri_boun-swe-573)](https://sonarcloud.io/dashboard?id=altugcagri_boun-swe-573)

# Social Media Exploration Platform
                                      Boğaziçi University Software Engineering Department

                                    Swe 573 - Fundamentals of Software Engineering 2019/Spring
                                    
                                  
## Table of contents
* [Project Management Structure Document](docs/ProjectManagementStructure.md "Project Management Structure")


## Installation

In order to install and run the project [Java](https://www.java.com) Jdk v8+ and [Gradle](https://gradle.org/) must be installed.

Set JAVA_HOME like "C:\Program Files\Java\jdk1.8.0_12", "bin" folder is not required. Otherwise gradle tasks can fail.

In order to run the project on docker environment [Docker](https://docs.docker.com/) must be installed.

After installation  of Java, Gradle and Docker, you can clone the project from the [repository](https://github.com/altugcagri/boun-swe-573.git) into your workspace.

## Build

After cloning the project go to workspace and run the gradle command:

```sh
$ .\gradlew build
```

in order to run tests run the gradle command:

```sh
$ .\gradlew test
```

## Run Without Docker Containerization

After building the project running the project requires the steps below;

Since project requires eureka-server and zuul-server, first these two services should run respectively.

com.altugcagri.smep-service:

```sh
$ java -jar build/libs/com.altugcagri.smep-0.0.1-SNAPSHOT.jar
```

## Run With Docker Containerization

After building the project, Simply run the commands according to your scalability choice;

In the directory where Dockerfile exists

Build image of BackEnd:

```sh
$ docker build -f Dockerfile --tag={ContainerName} .
```

Run Container of BackEnd:

```sh
$ docker run -p 8080:8080 {ContainerName}
```

Build image of BackEnd:

```sh
$ docker build -f WebDockerfile --tag={ContainerName} .
```

Run Container of BackEnd:

```sh
$ docker run -p 5000:5000 {ContainerName}
```

### Guides
The following guides illustrates how to use certain features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

