### This working set contains samples on how to read logging level from externalized config server into logback configuration


* Eureka-Server : Eureka registration server running at 8080
* Eureka-Config-Server: Configuration server running at 8888
* Eureka-Client-logservices : Eureka discovery client running at 8089

#### Eureka-Config-Server 

application.yml : 
```
server:
  port: 8888

spring:
  application:
    name: eureka-config-server
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
          search-locations: classpath:/
```
eureka-client-logservices.yml:

```
level: error
```

#### Eureka-Client-LogServices

bootstrap.yml: 
```
spring:
  application:
    name: eureka-client-logservices
  cloud:
    config:      
       uri: http://localhost:8888
       ```
application.yml : 

```
server:
  port: 8089
  
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    serviceUrl:
      defaultZone: ${eureka.service.url:http://localhost:8080/eureka}

management:
  security:
    enabled: false
    

  # logback.xml is defined here inorder to override the default configuration 
logging:
  config: classpath:log/logback.xml
  ```
