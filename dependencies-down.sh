docker-compose -f src/main/docker/postgresql.yml stop
docker-compose -f src/main/docker/keycloak.yml stop
docker-compose -f src/main/docker/jhipster-registry.yml stop
docker-compose -f src/main/docker/elasticsearch.yml stop

docker-compose -f src/main/docker/postgresql.yml rm
docker-compose -f src/main/docker/keycloak.yml rm
docker-compose -f src/main/docker/jhipster-registry.yml rm
docker-compose -f src/main/docker/elasticsearch.yml rm
