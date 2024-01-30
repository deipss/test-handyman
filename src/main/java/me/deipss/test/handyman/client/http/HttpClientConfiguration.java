package me.deipss.test.handyman.client.http;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class HttpClientConfiguration {

    public static final String CLOSEABLE_HTTP_CLIENT = "closeableHttpClient";

    @Bean(CLOSEABLE_HTTP_CLIENT)
    public CloseableHttpClient closeableHttpClient(){
        CloseableHttpClient client = HttpClients.custom().setConnectionTimeToLive(3, TimeUnit.MINUTES).build();
        return client;
    }

}
