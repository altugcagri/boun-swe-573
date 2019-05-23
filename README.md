[![CircleCI](https://circleci.com/gh/altugcagri/boun-swe-573/tree/dev.svg?style=svg&circle-token=8d8c2fc0288c5c84c31d65acd89cb4fe273a3b69)](https://circleci.com/gh/altugcagri/boun-swe-573/tree/dev)

[![Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=altugcagri_boun-swe-573)](https://sonarcloud.io/dashboard?id=altugcagri_boun-swe-573)

# Social Media Exploration Platform
                                      Boğaziçi University Software Engineering Department

                                    Swe 573 - Fundamentals of Software Engineering 2019/Spring
                                    
                                  
## Table of contents
* [Project Management Structure Document](docs/ProjectManagementStructure.md "Project Management Structure")


## Installation

In order to install and run the backend project [Java](https://www.java.com) Jdk v8+ and [Gradle](https://gradle.org/) must be installed.

In order to install and run the frontend project [Node](https://nodejs.org/en/) must be istalled

Set JAVA_HOME like "C:\Program Files\Java\jdk1.8.0_12", "bin" folder is not required. Otherwise gradle tasks can fail.

In order to run the projects on docker environment [Docker](https://docs.docker.com/) must be installed.

After installation  of Java, Gradle and Docker, you can clone the project from the [repository](https://github.com/altugcagri/boun-swe-573.git) into your workspace.

## Build BackEnd

After cloning the project go to workspace and run the gradle command:

```sh
$ .\gradlew build
```

in order to run tests run the gradle command:

```sh
$ .\gradlew test
```

## Build FrontEnd

After cloning the project go to web folder under the workspace and run the commands respectively;

```sh
$ npm install
```

Dowloads necessary packages and dependencies.

```sh
$ npm run build
```

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

## Run BackEnd Without Docker

After building the project running the project requires the steps below;

com.altugcagri.smep-service:

```sh
$ java -jar build/libs/com.altugcagri.smep-0.0.1-SNAPSHOT.jar
```

It runs the backend project

## Run FrontEnd Without Docker

After building the project running the project requires the steps below;

Go to web folder under the workspace

Since project requires com.altugcagri.smep-service, it is recomended to run backend first.

frontend:

```sh
$ npm install -g serve
```

It downloads and install necessery serving packages.

```sh
$ serve -s build
```
It runs the frontend project 


## Build and Run With Docker

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

