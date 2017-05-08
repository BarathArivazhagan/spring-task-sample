package com.accenture.adce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
public class Application {
	
	@Configuration
	@EnableSwagger2
	public class SwaggerConfig {                                    
	    @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.basePackage("com.accenture.adce"))              
	          .paths(PathSelectors.any())                          
	          .build()
	          .apiInfo(apiInfo());
	    }
	    private ApiInfo apiInfo() {
	        ApiInfo apiInfo = new ApiInfo(
	          "My REST API",
	          "Some custom description of API.",
	          "API TOS",
	          "Terms of service",
	          "myeaddress@company.com",
	          "License of API",
	          "API license URL");
	        return apiInfo;
	    }

	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@RequestMapping("/chartConfiguration")
	public ChartConfiguration saveChartConfiguration(@RequestBody ChartConfiguration chartConfig){
		return chartConfig;
	}
	
	
}
