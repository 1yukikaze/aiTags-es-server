package com.yukikaze.webuiesdemo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebuiEsDemoApplication {
    @Value("${esHttp}")
    private String esHttp;
    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(esHttp)));
    }

    public static void main(String[] args) {
        SpringApplication.run(WebuiEsDemoApplication.class, args);
    }

}
