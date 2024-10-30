package com.example.wishlink_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WishlinkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlinkBackendApplication.class, args);
	}

	// @Bean
	// public WebMvcConfigurer corsConfigurer(){
	// 	return new WebMvcConfigurer() {{}
	// 		public void addCorsMappings(CorsRegistry registry){
	// 			registry.addMapping("/**")
	// 			.allowedMethods("*")
	// 			.allowedOrigins("http://localhost:3000");
	// 		}
	// 	};
	// }

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:3000");
            }
        }; 
    }
	//,"http://192.168.0.113:3000" "http://192.168.20.134:3000 " http://localhost:3000  "http://172.18.128.1:3000"

}
