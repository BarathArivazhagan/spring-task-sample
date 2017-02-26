### This working set contains samples on how to read logging level from externalized config server into logback configuration


* Eureka-Server : Eureka registration server running at 8080
* Eureka-Config-Server: Configuration server running at 8888
* Eureka-Client-logservices : Eureka discovery client running at 8089



#### Eureka-Config-Server 

##### application.yml : 
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
##### eureka-client-logservices.yml:

```
level: error
```

#### Eureka-Client-LogServices

##### bootstrap.yml: 
```
spring:
  application:
    name: eureka-client-logservices
  cloud:
    config:      
       uri: http://localhost:8888
```
##### application.yml : 

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
    
logging:
  config: classpath:log/logback.xml
  ```
  
  ##### logback.xml: 
 ``` 
  <?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true">
 <springProperty scope="" name="LEVEL" source="level"   defaultValue="info"/>  

   <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
		<layout class="ch.qos.logback.classic.PatternLayout">
			<Pattern>
				%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
			</Pattern>
		</layout>
	</appender>
	<logger name="com.barath.app" level="${LEVEL}"
		additivity="false">
		<appender-ref ref="CONSOLE" />
	</logger>
   
    <root level="${LEVEL}">
         <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
      
  ```
  
