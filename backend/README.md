# Backend
1) Starting only database: (then starting application from some favourite IDE)  
   `docker compose -f docker-compose-dev.yml up -d database`
2) Starting only application: (assuming you have PostgreSQL server on your local machine, but then you have to change port in `application-dev.yaml` as default port for PostgreSQL is 5432)   
   `docker compose -f docker-compose-dev.yml up -d app`
3) Starting database and application  
   `docker compose -f docker-compose-dev.yml build` &   
   `docker compose -f docker-compose-dev.yml up`

If starting database as Docker container, you have to connect to server exposed on port 5433 using credentials provided in `docker-compose-dev.yml`