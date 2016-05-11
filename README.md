# README #
GitHubFacade Servcie aiming to retrieve user's repo summary.

## Setting up ##

### Summary of set up ###
Gradle is used as a build tool. Use gradle wrapper to make sure that build tool is consistent across environment
* Running the service
Execute 

```
#!shell

gradlew clean build run
```
 
task to run the application.
* Application url
Head over to
[http://127.0.0.1:8080/repositories/user-name/repo-name](http://127.0.0.1:8080/repositories/user-name/repo-name)
where user-name and repo-name are parameters of repo you want to check.

* Profiles
Run application with 

```
#!shell

spring.profiles.active=test
```
To enable swagger interface. Access the swagger at
[http://127.0.0.1:8080/swagger/index.html](http://127.0.0.1:8080/swagger/index.html)
* Tests