# TopQuizzApi - SpringBoot application

## Maven project

### Dependencies using in project

#### Spring boot fonctionning
- spring-boot-starter-parent 
- spring-boot-starter-web

#### MySql fonctionning
- spring-boot-starter-data-jpa
- mysql-connector-java

#### Others dependencies
- commons-lang3 -> Utilty fonciton
- lombok -> Getters/Setters/Constructors tools

## How to add a new service (name: XXX)
A service was composed by few classes:
- A controller: XXXController (in package Controller)
- A service composed by:
-- an interface: XXXService (in package Service)
-- An implementation: XXXServiceImpl (in package Service/impl)

In the controller class, puts fonction which made link with URL adresses (Exemple: UserController)
Create function in XXXService, and implement in XXXServiceImpl

For each function, add to AppService methods which call XXXService methods

Use AppService to make test with another services

## Android part's
Which URL call

### Project informations

|Service|Url|
|----|----|
|Tomcat|http://54.93.98.119:8090/|
|MySql|http://54.93.98.119/phpmyadmin/index.php|

### Urls
**/user/add**
Params POST:
- pseudo (String)
- mail (String)
- password (String)

**/get/**
Params POST:
- pseudo (String)

**getByMail**
Params POST:
- mail (String)
