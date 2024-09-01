# Kafka and OpenSearch Integration

This project demonstrates a full-fledged integration of Apache Kafka with OpenSearch, using Docker to manage the deployment of Kafka brokers, Kafka UI, OpenSearch, and OpenSearch Dashboards.

## Table of Contents

- [Kafka and OpenSearch Integration](#kafka-and-opensearch-integration)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Project Structure](#project-structure)
  - [Prerequisites](#prerequisites)
  - [Setup Instructions](#setup-instructions)
  - [Running the Project](#running-the-project)
  - [Accessing the Kafka UI](#accessing-the-kafka-ui)
  - [Accessing OpenSearch Dashboards](#accessing-opensearch-dashboards)
  - [Stopping the Services](#stopping-the-services)
  - [Additional Notes](#additional-notes)
  - [Contributing](#contributing)

## Overview

This project showcases how to set up a Kafka cluster with multiple brokers and integrate it with OpenSearch for indexing and querying data. The setup includes:

- A Kafka cluster with three brokers.
- Kafka UI for managing and monitoring Kafka topics, producers, and consumers.
- OpenSearch for storing and indexing data.
- OpenSearch Dashboards for visualizing the data stored in OpenSearch.

## Project Structure

```plaintext
Java-Kafka-Project/
├── src/
│   └── main/
│       └── java/
│           └── demo/
│               └── kafka/
│                   ├── common/
│                   │   ├── BaseKafkaClient.java
│                   │   ├── AbstractKafkaProducer.java
│                   │   └── AbstractKafkaConsumer.java
│                   ├── producer/
│                   │   ├── WikimediaChangeHandler.java
│                   │   └── WikimediaChangeProducer.java
│                   └── consumer/
│                       └── OpensearchConsumer.java
│
├── docker-compose.yml
└── build.gradle
```

- **`common/`**: Contains shared base classes for Kafka clients.
- **`producer/`**: Contains the Kafka producer classes.
- **`consumer/`**: Contains the Kafka consumer classes.
- **`docker-compose.yml`**: Docker Compose configuration to run Kafka, OpenSearch, and their associated services.
- **`build.gradle`**: Gradle build file for managing project dependencies.

## Prerequisites

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/install/) installed on your machine.
- Java 11 or higher installed.

## Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/Java-Kafka-OpenSearch-Project.git
   cd Java-Kafka-OpenSearch-Project
   ```

2. **Build the project:**

   ```bash
   ./gradlew build
   ```

3. **Start the Docker containers:**

   ```bash
   docker-compose up -d
   ```

This will start the Kafka cluster, Kafka UI, OpenSearch, and OpenSearch Dashboards.

## Running the Project

After setting up the project, you can run the Kafka producer and consumer:

1. **Run the Wikimedia Change Producer:**

   ```bash
   ./gradlew run --args='producer.WikimediaChangeProducer'
   ```

2. **Run the OpenSearch Consumer:**

   ```bash
   ./gradlew run --args='consumer.OpensearchConsumer'
   ```

## Accessing the Kafka UI

Kafka UI will be accessible via your web browser at:

```plaintext
http://localhost:8080
```

Use the following credentials:

- **Username:** `admin`
- **Password:** `pass`

## Accessing OpenSearch Dashboards

OpenSearch Dashboards will be accessible via your web browser at:

```plaintext
http://localhost:5601
```

This interface allows you to visualize and interact with the data stored in OpenSearch.

## Stopping the Services

To stop all running Docker containers, run:

```bash
docker-compose down
```

## Additional Notes

- The project is set up to run as a single-node OpenSearch cluster for simplicity. For production use, consider expanding this setup to a multi-node cluster.
- The Kafka brokers are configured to use different ports (29092, 29094, 29096) for external access, allowing for easy scalability and load balancing.

## Contributing

If you would like to contribute to this project, feel free to submit a pull request or open an issue on GitHub.
