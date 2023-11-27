
# Dockerized Microservices Project

This project contains a set of microservices running in Docker containers, orchestrated with Docker Compose. The microservices include PostgreSQL, pgAdmin, RabbitMQ, Redis, MongoDB, Eureka Server, Gateway, Transaction Service, Account Service, and Customer Support Service.

## Prerequisites

Make sure you have the following software installed on your machine:

- Docker: [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: [Install Docker Compose](https://docs.docker.com/compose/install/)

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/RayKanavheti/ibank-app.git
   cd ibank-app
   ```

2. Build and start the Docker containers:

   ```bash
   docker-compose up -d
   ```

   This command will download the required images, build the necessary containers, and start the services in detached mode.

3. Accessing Services:

   - PostgreSQL: [http://localhost:5432](http://localhost:5432)
     - Username: postgres
     - Password: postgres
   - pgAdmin: [http://localhost:5050](http://localhost:5050)
     - Email: admin@admin.com
     - Password: postgres
   - RabbitMQ Management: [http://localhost:15972](http://localhost:15972)
     - Username: backend_service
     - Password: pass123
   - Redis: [localhost:6379](localhost:6379)
   - MongoDB Express: [http://localhost:8081](http://localhost:8081)
     - Username: root
     - Password: mongodb
   - Eureka Server: [http://localhost:8761](http://localhost:8761)
   - Gateway: [http://localhost:8085](http://localhost:8085)
   - Transaction Service: [http://localhost:8086](http://localhost:8086)
   - Account Service: [http://localhost:8089](http://localhost:8089)
   - Customer Support Service: [http://localhost:8087](http://localhost:8087)

## Stopping the Services

To stop the services, run:

```bash
docker-compose down
```

This will stop and remove the containers, networks, and volumes created by `docker-compose up`.

## Notes

- Make sure no other services are running on the required ports (5432, 5050, 15972, 6379, 27017, 8081, 8761, 8085, 8086, 8089, 8087) to avoid conflicts.
- Ensure that your machine meets the system requirements for running Docker.

---
