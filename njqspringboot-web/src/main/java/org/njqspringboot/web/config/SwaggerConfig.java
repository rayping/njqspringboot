package org.njqspringboot.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan("org.njqspringboot.web.controller.api")

/**
 * http://host:port/rootPath/swagger-ui.html
 */
public class SwaggerConfig extends WebMvcConfigurerAdapter{
	
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.njqspringboot.web.controller.api"))
                .paths(path())  //PathSelectors.any()  Predicates.or(Predicates.containsPattern(""))
                .build();
    }

	
    private Predicate<String> path() {
		return PathSelectors.ant("/api/**");
	}

	private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("Spring Boot Sample REST APIs")
                .description("The APIs here demonstrate creating a service built with Spring Boot")
                /*.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact("Niklas Heidloff")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")*/
                .version("1.0")
                .build();
        return apiInfo;
    }
    
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
}
