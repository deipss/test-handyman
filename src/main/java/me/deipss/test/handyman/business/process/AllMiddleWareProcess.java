package me.deipss.test.handyman.business.process;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.business.BaseProcess;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.client.dubbo.DubboRequest;
import me.deipss.test.handyman.client.jdbc.JdbcRequest;
import me.deipss.test.handyman.client.redis.RedisRequest;
import me.deipss.test.handyman.client.rocketmq.RocketmqRequest;
import me.deipss.test.handyman.context.ProcessContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AllMiddleWareProcess extends BaseProcess {


    @Order(1)
    public int jdbc(String  jdbc) {
        System.out.println(jdbc);
        JdbcRequest jdbcRequest = new JdbcRequest();
        jdbcRequest.setSql("select * from t_heart_beat limit 10");
        ClientResponse<Object> execute = jdbcClient.execute(jdbcRequest);
        System.out.println(JSON.toJSONString(execute));
        ProcessContext.get().getGlobalMap().put("redis","redisd");
        return 1;
    }

    @Order(2)
    public int redis(String redis) {
        System.out.println(redis);
        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setCommand("keys *");
        ClientResponse<Object> execute = redisClient.execute(redisRequest);
        System.out.println(JSON.toJSONString(execute));
        ProcessContext.get().getGlobalMap().put("mq","me");
        return 1;
    }

    @Order(3)
    public int mq(String mq) {
        System.out.println(mq);
        RocketmqRequest rocketmqRequest = new RocketmqRequest();
        rocketmqRequest.setMsg("dsdsds");
        rocketmqRequest.setTopic("test");
        rocketmqRequest.setTags("test");
        rocketmqClient.execute(rocketmqRequest);
        System.out.println(1);
        ProcessContext.get().getGlobalMap().put("dubbo","d");

        return 1;
    }

    @Order(4)
    public int dubbo(String  dubbo) {
        System.out.println(dubbo);
        DubboRequest dubboRequest = new DubboRequest();
        dubboRequest.setInterfaceName("me.deipss.jvm.sandbox.inspector.debug.api.facade.PaymentFacade");
        dubboRequest.setMethod("payment");
        dubboRequest.setParamNameList(new String[]{"me.deipss.jvm.sandbox.inspector.debug.api.request.PaymentRequest"});
        dubboRequest.setParamObjList(new Object[]{});
        dubboClient.initClient(dubboRequest);
        ClientResponse<Object> execute = dubboClient.execute(dubboRequest);
        System.out.println(JSON.toJSONString(execute));        return 1;
    }
}
