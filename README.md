# FedHipster Base Application

## Database configuration

The security settings in `src/main/resources/application.yml` and `src/main/resources/application-dev.xml` are configured for this image.

```yaml
spring:
  ...
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:postgresql://localhost:5432/comet
    username: app
    password:
```

## Dependencies

The application depends on three external services: a database (postgres), an authentication server (Keycloak), and a registry (using Netflix Zuul).

```
docker-compose -f src/main/docker/keycloak.yml up
docker-compose -f src/main/docker/postgresql.yml up
docker-compose -f src/main/docker/jhipster-registry.yml up
```

If you chose to use a locally running database you can ignore

## Running the application

Run the following commands to run microservice and serve the static front end content

    ./mvnw

(Optional) Run the following to get live-reload of front end content

    npm start

## Building for production

### Packaging as jar

To build the final jar and optimize the comet application for production, run:

    ./mvnw -Pprod clean verify

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar target/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Testing

To launch your application's tests, run:

    ./mvnw verify

### Client tests

Unit tests are run by [Jest][] and written with [Jasmine][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    npm test

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```
