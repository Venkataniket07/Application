# BackendApplication

Online shopping Backend-based spring-boot application

1. Info related to database is provided in the application.properties file. Changes can be made to connect to your local
   DB.
2. First start docker-compose-mssql.yml sql-server container, once it's up and running you can start
   docker-compose-application.yml application container.
3. DB connection info can be changed in docker-compose file also.
4. If any changes are made then run maven command to apply the changes and then restart the docker container.
5. Base-url info for requests through postman:
    1. Customer: "http://locahost:8080/backendApplication/Customer/" + request.