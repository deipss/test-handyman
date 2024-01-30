package me.deipss.test.handyman.client.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

    public static final String ES_REST_CLIENT = "esRestClient";
    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private String port;

    @Bean(ES_REST_CLIENT)
    public RestHighLevelClient initRestClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, Integer.parseInt(port), "http")
                ));
        return client;
    }

}
