package me.deipss.test.handyman.client;


import java.util.Map;

/**
 * R request
 * T client
 * K return
 *
 */
public abstract class AbstractClient<R extends ClientRequest, T,K> implements BaseClient<R,T, K> {

    /**
     * client map
     * key = union key
     * map = client e.g. http client
     */
    protected Map<String,T> clientMap;

}
