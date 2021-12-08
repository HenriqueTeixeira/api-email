# Configurações da aplicação.

## Criar rede docker para sistema api-email
```
docker network create devcorp-net
```

## Baixando a imagem do PostgreSql no Docker Hub e rodando no Docker local.
```
docker pull postgres:12-alpine

docker run -p 5432:5432 --name devcorp-pg12 --network devcorp-net -e POSTGRES_PASSWORD=devcorp123 -e POSTGRES_DB=devcorp postgres:12-alpine