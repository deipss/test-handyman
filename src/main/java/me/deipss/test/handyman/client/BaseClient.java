package me.deipss.test.handyman.client;


public interface BaseClient<T extends ClientRequest> {

    Object execute(T request);
}
