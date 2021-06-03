# FavoriteRecipesServer

The favorite recipes application server, provides a security application that handle add,create, update, remove and other actions related with recipes.


## Documentation

in the /documents folder, you can have access to architecture flow, architecture high level draw and testing cases using GivenWhatThen style.

## Deploy and Run

Follow the steps below to run locally.

### Using docker

In order to use docker as the deployment tool of this application, make sure to have docker installed: [Docker Instalation](https://docs.docker.com/engine/install/).

1. Open the project and go to sources\backend\FavouriteReceipes
2. Execute the command: docker build -t favorite-recipes:1.0.0 .
> Note: this command will create a docker image locally, you can check this image executing `docker images`
3. After the build, you must to run the container:
``docker run -p 8080:8080 -d favorite-recipes:1.0.0``
4. Successfully deployed container

### Running locally

>Note: For unning locally, you need to have the maven installed [Maven instalation](https://maven.apache.org/install.html)
1. Open the project and go to sources\backend\FavouriteReceipes
2. execute: ``mvn clean install`` 


### On live application

The server application is and running 7h a day in Heroku, you can point your postman requests to: https://favorite-recipe-server.herokuapp.com/.
In documentation folder will be saved some samples to use via POSTMAN.
