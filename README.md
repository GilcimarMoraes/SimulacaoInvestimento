# painel-investimento

## Projeto desenvolvido para Semana de Arquitetura do CaixaVerso

### Usuário e senha para acesso ao sistema

- Usuário: admin
- Senha: admin


- Usuário: user
- Senha: user


### Banco de dados

A aplicação usa PostgreSQL. Por padrão:

- URL: `jdbc:postgresql://localhost:5432/simulacao-investimento`
- Usuário: `postgres`
- Senha: `157157` (padrão atual em `application.properties`)

Crie o banco antes de subir:

```bash
createdb simulacao-investimento
```

As variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD` sobrescrevem esses valores.

O schema e o seed (`src/main/resources/import.sql`) são criados automaticamente
na inicialização (`quarkus.hibernate-orm.schema-management.strategy=drop-and-create`).
Em produção, troque por Flyway/Liquibase para não recriar as tabelas a cada start.

### Login (API)

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"usuario":"admin","senha":"admin"}')

curl -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/investimentos/1
```

O login retorna o JWT em texto puro. Use `Authorization: Bearer <token>` nas rotas
protegidas.

### Simulação de investimento

```bash
curl -s -X POST http://localhost:8080/simular-investimento \
  -H 'Content-Type: application/json' \
  -d '{"clienteId":123,"valor":10000.00,"prazoMeses":12,"tipoProduto":"CDB"}'
```

O produto é validado pelo `tipoProduto` informado (primeiro produto do tipo,
ordenado por id). O `valorFinal` é calculado com juros compostos a partir da
rentabilidade anual do produto validado.

### Chaves JWT

`publicKey.pem` e `privateKey.pem` formam o par de demonstração usado para assinar
e validar os tokens. Para um ambiente real, gere um novo par, mantenha a chave
privada fora do repositório e injete-a via secret/volume.

### Testes

```bash
./mvnw test
```

A suíte inclui testes de integração que sobem a aplicação com H2 e validam o
login (`AutenticacaoResourceTest`) e o contrato dos sete endpoints
(`ContratoApiTest`).

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/desafio_api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
