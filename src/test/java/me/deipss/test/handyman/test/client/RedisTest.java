package me.deipss.test.handyman.test.client;

import com.alibaba.fastjson.JSON;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.client.redis.RedisClient;
import me.deipss.test.handyman.client.redis.RedisRequest;
import org.junit.Test;

public class RedisTest {

    @Test
    public void test(){
        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setCommand("keys *");
        RedisClient redisClient = new RedisClient();
        redisClient.initClient();
        ClientResponse<Object> execute = redisClient.execute(redisRequest);
        System.out.println(JSON.toJSONString(execute));
    }
}
