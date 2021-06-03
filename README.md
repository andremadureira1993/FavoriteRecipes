# FavoriteRecipesServer

The favorite recipes application server, provides a security application that handle add,create, update, remove and other actions related with recipes.

## Documentation

in the /documents folder, you can have access to architecture flow, architecture high level draw and testing cases using GivenWhatThen style.

- [Architecture Definitions](https://github.com/andremadureira1993/FavoriteRecipes/blob/develop/documentation/architecture/Architecture-definitions.md)
- [High-Level Flows](https://github.com/andremadureira1993/FavoriteRecipes/blob/develop/documentation/architecture/HighLevelFlows.pdf)
- [Testing Cases - GivenWhenThen](https://github.com/andremadureira1993/FavoriteRecipes/blob/develop/documentation/architecture/TestingCases-GivenWhenThen.md)
- [OpenAPI generator](https://github.com/andremadureira1993/FavoriteRecipes/blob/develop/documentation/architecture/openapi.yaml)

## Deploy and Run

Follow the steps below to run locally.

### Backend

#### Using docker

In order to use docker as the deployment tool of this application, make sure to have docker installed: [Docker Instalation](https://docs.docker.com/engine/install/).

1. Open the project and go to sources\backend\FavouriteReceipes
2. Execute the command: docker build -t favorite-recipes:1.0.0 .
> Note: this command will create a docker image locally, you can check this image executing `docker images`
3. After the build, you must to run the container:
``docker run -p 8080:8080 -d favorite-recipes:1.0.0``
4. Successfully deployed container

#### Running locally

>Note: For unning locally, you need to have the maven installed [Maven instalation](https://maven.apache.org/install.html)
1. Open the project and go to sources\backend\FavouriteReceipes
2. execute: ``mvn clean install`` 

### On live application

The server application is and running 7h a day in Heroku, you can point your postman requests to: https://favorite-recipe-server.herokuapp.com/.
In documentation folder will be saved some samples to use via POSTMAN.

> Note: Since I'm using a free dyno in heroku, the server may need some seconds after received an request to startup the application

### Frontend

#### Running locally

Access the sources/frontend and execute the command `npm start` in order to get the frontend running.

#### Upload a production package

Was release a build package in sources/frontend/build for deployments with the most complete version.

### On live application

The website is live 24h a day in Netlify and the access can be created in the singup page with any username and password.
> In any case of failures creating the user, use the following access to login. Username/Passowrd: andre/andre

## Technologies

- Java 11
- Spring Boot
- JUnit 5
- Mockito
- Maven
- Open-Api Generator
- Swagger
- Reactjs
- Javascript
- MongoDB
- Heroku
- Netlify
- Intellij
- Github
- Draw-io
