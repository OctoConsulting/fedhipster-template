docker-compose -f src/main/docker/postgresql.yml up -d
docker-compose -f src/main/docker/keycloak.yml up -d
docker-compose -f src/main/docker/jhipster-registry.yml up -d
docker-compose -f src/main/docker/elasticsearch.yml up -d
