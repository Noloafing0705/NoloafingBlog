package com.noloafing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
* Swagger2配置
* @author
*/
@Configuration
@EnableSwagger2
@EnableWebMvc
public class Swagger2Config implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
 
			registry.addResourceHandler("/**").addResourceLocations(
					"classpath:/static/");
			registry.addResourceHandler("swagger-ui.html").addResourceLocations(
					"classpath:/META-INF/resources/");
			registry.addResourceHandler("/webjars/**").addResourceLocations(
					"classpath:/META-INF/resources/webjars/");
			WebMvcConfigurer.super.addResourceHandlers(registry);
	}
 
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES) //配置请求头
				.produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.noloafing.controller"))
				.paths(PathSelectors.any()).build();
	}

	//配置content type
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
			new HashSet<String>(Arrays.asList("application/json"));//,"application/xml"
 
	private ApiInfo apiInfo() {
		Contact contact = new Contact("Noloafing", "https://blog.csdn.net/m0_51972565?type=blog", "1845008982@qq.com");
		return new ApiInfoBuilder()
				.title("Noloafing博客API文档")
				.description("从三更草堂学习而来的博客项目")
				.contact(contact)
				.version("1.0").build();
	}
}