package me.deipss.test.handyman.test.client;

import com.alibaba.fastjson.JSON;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.client.redis.LocalRedisClient;
import me.deipss.test.handyman.client.redis.RedisRequest;
import org.junit.Test;

public class RedisTest {

    @Test
    public void test(){
        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setCommand("keys *");
        LocalRedisClient localRedisClient = new LocalRedisClient();
        localRedisClient.initClient();
        ClientResponse<Object> execute = localRedisClient.execute(redisRequest);
        System.out.println(JSON.toJSONString(execute));
    }
}
