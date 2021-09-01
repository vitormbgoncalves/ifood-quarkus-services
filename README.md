<p align="center">
  <img width="180" height="180" src="https://design.jboss.org/quarkus/logo/final/PNG/quarkus_icon_rgb_1024px_default.png">
</p>

<h1 align="center">       
   iFood Quarkus Microservices
</h1>

This project was developed based on Udemy's course [*__Desenvolvimento Web com Quarkus__*](https://www.udemy.com/course/des-web-quarkus/), taught by [Vinicius Ferraz](https://github.com/viniciusfcf).

The objective of this project was to create three microservices for a food delivery system, based on the iFood app functionalities. This project was built with Quarkus: Supersonic Subatomic Java framework and its main extensions, Microprofile implementation, MongoDB, PostgreSQL, JUnit 5, Testcontainers, Database Rider, REST Assured, MapStruct, Keycloak, Jaeger, Prometheus, Grafana, Elastic Stack, Apache ActiveMQ, Apache Kafka, Kubernetes and GraalVM.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `crud-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/crud-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/crud-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.


## Quick note

This project was developed and runs on Linux.
