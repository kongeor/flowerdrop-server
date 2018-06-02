# FlowerDrop

How to start the FlowerDrop application
---

## Prepare dev DB:

```
postgres=# create database flowerdrop_log_dev;
postgres=# create user flowerdrop_log_dev with password 'flowerdrop_log_dev';
postgres=# alter database flowerdrop_log_dev owner to flowerdrop_log_dev ;
postgres=# \c flowerdrop_log_dev;
postgres=# create table flower_drop_log (
    id serial primary key,
    created_date timestamp,
    updated_date timestamp,
    description text
);
alter table flower_drop_log owner to flowerdrop_log_dev;
```

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/flowerdrop-server-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
