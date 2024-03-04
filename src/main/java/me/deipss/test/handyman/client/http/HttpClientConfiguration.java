package me.deipss.test.handyman.client.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class HttpClientConfiguration {

    public static final String CLOSEABLE_HTTP_CLIENT = "closeableLocalHttpClient";

    @Bean(CLOSEABLE_HTTP_CLIENT)
    public CloseableHttpClient closeableHttpClient(){
        log.info("closeableHttpClient has initialed");
        return HttpClients.custom().setConnectionTimeToLive(7, TimeUnit.MINUTES).build();
    }

}
