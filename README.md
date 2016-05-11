# README #
GitHubFacade Service aiming to retrieve user's repo summary. Service is based on a Java 8 with Spring Boot, producing Uber-jar with Apache Tomcat embedded.

## Summary of set up ##
Gradle is used as a build tool. Use gradle wrapper to make sure that build tool is consistent across environment

## Running the service ##

### With gradle task ###
Execute 

```
#!shell

gradlew clean build run
```
 
task to run the application.

### With java command ###
Alternatively you can first build jar with 

```
#!shell

gradlew clean build
```
This will produce jar file to /build/libs directory. You can run it with:

```
#!shell

java -jar build/libs/github-facade-service-1.0.0.jar
```

## Application url ##
Head over to
[http://127.0.0.1:8080/repositories/user-name/repo-name](http://127.0.0.1:8080/repositories/user-name/repo-name)
where user-name and repo-name are parameters of repo you want to check. For example:
[http://127.0.0.1:8080/repositories/yacekmm/IDEConfig](http://127.0.0.1:8080/repositories/yacekmm/IDEConfig)

## Tests ##
Unit and integration tests are implemented. 

### Unit tests ###
Unit tests are a part of build process and are executed with build:

```
#!shell

gradlew clean build
```

### Integration tests ###

Integration tests are longrunning and they require the internet access (since they actually access the GitHub API) therefore they are separated end executed with dedicated task:

```
#!shell

gradlew integrationTest
```


## Profiles ##
Run application with 

```
#!shell

spring.profiles.active=test
```
To enable swagger interface. Access the swagger at
[http://127.0.0.1:8080/swagger/index.html](http://127.0.0.1:8080/swagger/index.html)