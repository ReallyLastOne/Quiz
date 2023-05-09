# Backend
1) Starting only database: (then starting application from some favourite IDE)  
   `docker compose up -d database`
2) Starting only application: (assuming you have PostgreSQL server on your local machine, but then you have to change port in `application-dev.yaml` as default port for PostgreSQL is 5432)   
   `docker compose up -d backend`
3) Starting database and application  
   `docker compose up -d backend database`

If starting database as Docker container, you have to connect to server exposed on port 5433 using credentials provided in `docker-compose.yml`

# Frontend
To start the frontend of the application  
`docker compose up -d frontend`

# Complete application
To start frontend with backend and database, simply execute command:  
`docker compose up`

# Notes
1. You have to execute docker commands in `Quiz` folder,
2. Frontend application is available on port 80, backend on port 8080 and database on 5433,
3. If you made changes in some files and want to access these changes, you have to add `--build` flag to docker commands.
