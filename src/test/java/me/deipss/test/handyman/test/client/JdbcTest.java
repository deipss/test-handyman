package me.deipss.test.handyman.test.client;

import com.alibaba.fastjson.JSON;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.client.jdbc.JdbcClient;
import me.deipss.test.handyman.client.jdbc.JdbcRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JdbcTest {

    @Test
    public void test(){
        JdbcRequest jdbcRequest = new JdbcRequest();

        jdbcRequest.setSql("select * from t_heart_beat limit 10");
        JdbcClient jdbcClient = new JdbcClient();
        jdbcClient.initClient();
        ClientResponse<List<Map<String, Object>>> query = jdbcClient.query(jdbcRequest);
        System.out.println(JSON.toJSONString(query));
    }
}
