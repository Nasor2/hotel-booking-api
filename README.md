# Booking-api

A robust RESTful API for room booking management, built with Spring Boot 3 and Dockerized using Docker Compose. This API allows you to manage users, rooms, and their bookings, ensuring availability and data integrity.

## 🚀 Key Features

* **Booking Management:** Create, read, update, and delete bookings, with date validation to prevent overlaps.
* **Room Management:** Full CRUD for rooms, including number, type, and price per night.
* **User Management:** Full CRUD for users, including contact information.
* **Data Validation:** Uses `jakarta.validation` to ensure input data integrity.
* **Data Persistence:** Integrated with PostgreSQL as a relational database.
* **Database Migrations:** Schema management using [Flyway](https://flywaydb.org/).
* **Containerization:** Application and database deployment using Docker and Docker Compose for a consistent and reproducible environment.
* **API Documentation:** Automatically generated OpenAPI documentation (Swagger UI) to facilitate API exploration and usage.
* **DTO Mapping:** Uses Data Transfer Objects (DTOs) and mappers to separate the data layer from the presentation layer and avoid direct entity exposure.
* **Custom Exceptions:** Error handling with custom exceptions for resources not found (`ResourceNotFound`) and availability conflicts (`RoomNotAvailableException`).

## 🛠️ Technologies Used

* **Spring Boot:** Framework for rapid Java application development.
* **Maven:** Dependency management and build tool.
* **Java 17 (Eclipse Temurin):** Java runtime environment.
* **PostgreSQL:** Robust, open-source relational database.
* **Spring Data JPA/Hibernate:** Abstraction layer for relational database access.
* **Lombok:** Library to reduce boilerplate code in Java classes.
* **Flyway:** Tool to manage and apply database migrations.
* **Docker:** Platform to develop, ship, and run applications in containers.
* **Docker Compose:** Tool to define and run multi-container Docker applications.
* **Springdoc OpenAPI UI:** Automatic generation of OpenAPI 3 documentation (Swagger UI).
* **DTO (Data Transfer Object Pattern):** For data transfer between layers.

## ⚙️ Project Setup

### Prerequisites

Make sure you have the following installed:

* **Docker Desktop:** Includes Docker Engine and Docker Compose.
    * [Install Docker Desktop](https://www.docker.com/products/docker-desktop/)
* **Git:** To clone the repository.
    * [Install Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* **Maven** (**Optional**, if you want to build the JAR without Docker)
    * [Install Maven](https://maven.apache.org/install.html)
* **JDK 17** (**Optional**, if you want to run the app without Docker)
    * [Install Adoptium Temurin 17](https://adoptium.net/temurin/releases/)

### 🚀 Run the Application with Docker Compose

1. **Clone the repository:**

    ```bash
    git clone https://github.com/Nasor2/hotel-booking-api
    cd booking-api
    ```

2. **Create the environment variables file:**
    In the project root (where `compose.yaml` is located), create a file named `.env` and define the following variables for the database connection:

    ```env
    DB_NAME=booking_db
    DB_USER=myuser
    DB_PASSWORD=mypassword
    ```

    **Important!** Do not commit this `.env` file to your Git repository, especially if it contains sensitive credentials for production environments.

3. **Start services with Docker Compose:**
    From the project root directory, run the following command:

    ```bash
    docker compose up --build -d
    ```

    * `--build`: Rebuilds the images (useful for the first run or if you modified the `Dockerfile` or app code).
    * `-d`: Runs the containers in detached mode (in the background).

    Docker Compose will:
    * Build the Spring Boot app image using the `Dockerfile`.
    * Pull the PostgreSQL image if not already present.
    * Create containers for the database and your app.
    * Wait until the database is “healthy” before starting the app.
    * Run Flyway migrations automatically when the app starts.

4. **Check container status:**

    ```bash
    docker compose ps
    ```

    You should see both `db` and `app` as `running` and `healthy`.

### Stop the Application

To stop and remove the containers (and the associated network) while keeping the database volumes:

```bash
docker compose down
```
To stop and remove the containers, network, and all database volumes (this will permanently delete your database data — use with caution!):

```bash
docker compose down -v
```

## 🌐 API Endpoints and Documentation

Once the application is running, you can access the automatically generated API documentation provided by Springdoc OpenAPI (Swagger UI).

* **Swagger UI Documentation:** Open your browser and navigate to `http://localhost:8080/swagger-ui.html`
* **Base Endpoints:**
  * **Bookings:** `http://localhost:8080/api/v1/bookings`
  * **Rooms:** `http://localhost:8080/api/v1/rooms`
  * **Users:** `http://localhost:8080/api/v1/users`

Explore the Swagger UI interface to view all available endpoints (GET, POST, PUT, DELETE) and test them directly from the browser.

### Example Endpoints

* **Retrieve all bookings:** `GET /api/v1/bookings`
* **Create a new booking:** `POST /api/v1/bookings`
* **Get a room by ID:** `GET /api/v1/rooms/{id}`
* **Create a new user:** `POST /api/v1/users`

## 📁 Project Structure

```
booking-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nasor/bookingapi/
│   │   │       ├── controller/        
│   │   │       ├── dto/               
│   │   │       ├── exception/         
│   │   │       ├── mapper/            
│   │   │       ├── model/            
│   │   │       ├── repository/        
│   │   │       └── service/           
│   │   └── resources/
│   │       ├── application.properties 
│   │       └── db/migration/          
│   └── test/
│       └── java/...                   
├── pom.xml                            
├── Dockerfile                         
├── compose.yaml                       
└── .env                               
```

## 💻 Additional Notes

* **Data Persistence:** The `db_data` volume defined in `compose.yaml` ensures that your PostgreSQL data is preserved even if the containers are stopped or removed — unless you run `docker compose down -v`, which will permanently delete the database contents.
* **Healthcheck:** The `healthcheck` configuration in the `db` service ensures that the application only starts once the database is fully ready to accept connections.
* **Multi-stage Dockerfile:** The `Dockerfile` uses a multi-stage build to create a final lightweight and production-ready image optimized for deployment.

