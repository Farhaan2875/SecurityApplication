package com.SecurityApp.SecurityApplication.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig { //RestClient -> used to communicate with third party API/services in blocking manner -> code does nor run further until data is fetched from other server
    //it is synchronous / blocking

    @Value("${employeeService.base.url}")
    private String BASE_URL;

    @Bean
    @Qualifier("employeeRestClient") //gives bean a unique identity when multiple beans are there for RestClient class
    RestClient getEmployeeServiceRestClient(){  // Default header added to every HTTP request: tells server that request body data is in JSON format
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req,res) -> {
                    throw new RuntimeException("server error occurred");
                })
                .build();
    }
}
