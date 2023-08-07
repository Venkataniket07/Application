# BackendApplication
Online shopping Backend-based spring-boot application

1. Info related to database is provided in application.properties file. 
2. Wait till the mssql Server is up and running before starting the application container.
3. If any changes are made then run maven command to apply the changes and then restart the docker container.
4. If you're running the application in docker then the base-url:
        1.   Customer: "http://locahost:8080/backendApplication/Customer/" + request.