package me.deipss.test.handyman.client;


public interface BaseClient<R extends ClientRequest,T,K> {

    String DEFAULT_KEY = "default";

    ClientResponse<K> execute(R request);


    void initClient();



}
