## Testing scenarios (GivenWhenThen)

The following test scenarios represents possible actions, flows and results using the GivenWhenThen style in order to present the expect scenario results using a pattern


### Sign up Scenarios

- Successfully signup
  - Given  a new username creation
  - When the user is trying to create a new user in application
  - Then the user is successfully created

- Error signup
  - Given  a duplicated username creation
  - When the user is trying to create a new user in application with the same username already picked
  - Then the application throws an error saying that will not be able to create the user because already found
  
- Error signup - Empty Credentials
  - Given  invalid(null) request
  - When the user is trying to create a new user in application with the username and password empty or null
  - Then the application throws an error with BAD_REQUEST

- Successfully Login
  - Given a valid username and password already registered before
  - When user is trying to access the application
  - Then user will successfully receive the jwt token
  
- Error Login - Invalid credentials
  - Given a invalid username or password
  - When user is trying to access the application
  - Then the application throws an error with BAD_REQUEST
  
- Error Login - Empty/null credentials
  - Given a empty/null username or password
  - When user is trying to access the application
  - Then the application throws an error with BAD_REQUEST
  
- Successfully create recipe
  - Given a new recipe
  - When user is registering in application
  - Then return true after save in database
  
- Error save recipe - Duplicated Recipe
  - Given a recipe already saved in database
  - When user is saving an duplicate recipe
  - Then return error saying that recipe is already created
  
- Error save recipe - Invalid credentials
  - Given a empty/null recipe
  - When user is saving an new recipe
  - Then the application throws an error with BAD_REQUEST
  
- Successfully update recipe
  - Given a recipe already saved in database
  - When user is changing some informations of recipe
  - Then return true after update in database
  
- Error updating recipe - Invalid credentials
  - Given a empty/null recipe
  - When user is changing an existing recipe
  - Then the application throws an error with BAD_REQUEST
  
- Sucessfully get Recipe
  - Given a List of recipes
  - When user is searching for recipes
  - Then the application will return the list with items founded

- Sucessfully remove Recipe
  - Given a founded recipe in database
  - When user is deleting a recipe
  - Then the application will return an ok
  
- Successfully set recipe as favorite
  - Given a recipe
  - When user set a recipe as his favorite
  - Then the relation will be created in database
  
- Sucessfully remove favorite
  - Given a founded recipe favorite in database
  - When user is deleting a recipe favorite
  - Then the application will return an ok
