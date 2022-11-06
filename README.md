# Estudo de caso monitor de estados

### O presente projeto tem por objetivo explorar conceitos basicos do uso da feramenta ACTUATOR do spring boot.

## Passo a passo 

Versão JAVA: JDK 11

Primeiramente deve-se instalar o Docker, e na raiz do projeto executar o comando:

```bash 
docker-compose up
```

Isso fará com que sejam iniciados uma instancia do RabbitMQ e uma do banco de dados PostgreSQL.
Desse modo será possível iniciar o aplicativo Spring.

Uma vez o aplicativo iniciado deve-se acessar o endereço:

```
http://127.0.0.1:8080/actuator
```

Onde será listado todos os monitores habilitados, sendo eles e health, httptrace.

- httptrace: Lista o histórico de requisições armazenados em memória.

- health: lista a saude de conexão com bando de dados PostgreSQL e RabbitMQ.

## HTTPTRACE

Para facilitar a analize do HTTPTRACE foi disponibilizado o endpoint:

```
http://127.0.0.1:8080/cliente
```
Que pode ser acessado diretamente pelo Browser. Ao ser consumido o endpoint gera um registro no HTTPTRACE. 


