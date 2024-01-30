package me.deipss.test.handyman.client.redis;

import me.deipss.test.handyman.client.ClientRequest;
import lombok.Data;

@Data
public class RedisRequest extends ClientRequest {
    private String host;
    private String password;
    private String username;
    private String command;
    private int database;
    private int port;
}
