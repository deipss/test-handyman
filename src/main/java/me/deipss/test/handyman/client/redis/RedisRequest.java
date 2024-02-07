package me.deipss.test.handyman.client.redis;

import lombok.EqualsAndHashCode;
import me.deipss.test.handyman.client.ClientRequest;
import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class RedisRequest extends ClientRequest {
    private String host;
    private String password;
    private String username;
    private String command;
    private int database;
    private int port;
}
