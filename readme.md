# City List Application
This is an enterprise-grade city list application built using Spring Boot and Maven build system. The application allows users to browse through a paginated list of cities with corresponding photos and search for cities by name. Additionally, users with the Spring Security's role ROLE_ALLOW_EDIT can edit the city by changing its name or photo.

The initial list of cities is populated using the attached cities.csv file. City addition, deletion, and sorting are not in the scope of this task.
## Technical Stack
-Spring Boot
-Maven build system
-H2 database
-springframework security
- Docker
## How to Use
Users can browse the city list by navigating to the root endpoint ("/"). To search for a city by name, users can navigate to "/search" and provide a query parameter "name" with the name of the city.
To edit a city, users must have the role ROLE_ALLOW_EDIT and provide login credentials. The login credentials for the user with the required role are:

Login: username
Password: password

## Available Endpoints
-GET / - Retrieves the paginated list of cities with corresponding photos
-GET /search?name={cityName} - Retrieves the cities that match the given name query
-PUT /edit/{cityId} - Edits the city with the given id, requires ROLE_ALLOW_EDIT role and login credentials.
## Security
The application uses Spring Security for authentication and authorization. The only user with the ROLE_ALLOW_EDIT role is the one with the login credentials mentioned above. All other users have read-only access to the city list.
## Database
The application uses an H2 database to store the list of cities and their photos. The database is pre-populated with the data from the attached cities.csv file.
## Run Locally
The application may be run locally by means of the following command:
To run a Java Spring Boot project with a H2 database inside a Docker container, follow these steps:
####    - Open the terminal and navigate to the root directory of the project where the Dockerfile is located.
####    - Build the Docker image by running the following command:
######            docker build -t city-application:0.0.1 .
This command will start a new Docker container based on the city-application image and map port 8080 of the container to port 8080 of the host machine.
####    - Finally, run docker container with the following command:
######            docker run -d -p 8080:8080 -t city-application:0.0.1
This command will start the Spring Boot application inside the Docker container.

The application will be accessible at http://localhost:8080.
