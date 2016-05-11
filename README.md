# README #
GitHubFacade Service aiming to retrieve user's repo summary. Service is based on a Spring Boot.

## Summary of set up ##
Gradle is used as a build tool. Use gradle wrapper to make sure that build tool is consistent across environment

## Running the service ##
Execute 

```
#!shell

gradlew clean build run
```
 
task to run the application.

## Application url ##
Head over to
[http://127.0.0.1:8080/repositories/user-name/repo-name](http://127.0.0.1:8080/repositories/user-name/repo-name)
where user-name and repo-name are parameters of repo you want to check. For example:
[http://127.0.0.1:8080/repositories/yacekmm/IDEConfig](http://127.0.0.1:8080/repositories/yacekmm/IDEConfig)

## Tests ##
Unit and integration tests are implemented. 

### Unit tests ###
Unit tests are a part of build process. They can be executed with:

```
#!shell

gradlew test
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