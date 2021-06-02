The document provides the definitions of items regarding the architecture choices.

## User Experience

### Expected

Users should have access to the webpage with the following aspects:
- Fast
- Flexible

### How

The frontend will be developed in the top of ReactJS framework.


## Authentication

### Expected

It's expected that user can create a account and used it.

### How

Implementation of the create a account option, authentication using username and password, and for the Authorization will use a JWT token from spring security


## Restful patterns

### Expected

Use RESTful patterns in order to construct a microservice application

### How

Usage of Swagger and HTTPVerbs

## Database

### Expected

Its expected for the user to have the information in the website ASAP and there isn't any critical information that has to be saved in database, so we're looking for a non-relational database

### How

Implementation of mongodb as backend, plus the database should be constructed in the top of a docker-compose to allow costumer to run locally.
Deploy in MongoDB Atlas, with the location of Netherlands

## CI/CD

### Expected

Since we're developing a ready production application we need to make sure that we're going to have a production environment where all push to main branch will be deployed automatically

### How

-Frontend: Deploy via netlify that's a opensource website.
-Backend: Deploy via Heroku