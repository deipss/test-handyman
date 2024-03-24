package me.deipss.test.handyman.client.rocketmq;


import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class RocketmqClient extends AbstractClient<RocketmqRequest, DefaultMQProducer, SendResult> {

    @Override
    @SneakyThrows
    public ClientResponse<SendResult> execute(RocketmqRequest request) {
        Message message = new Message();
        message.setBody(request.getMsg().getBytes());
        message.setTopic(request.getTopic());
        message.setTags(request.getTags());
        SendResult send = clientMap.get(DEFAULT_KEY).send(message);
        return ClientResponse.<SendResult>builder().data(send).build();
    }

    @Override
    @SneakyThrows
    @PostConstruct
    public void initClient() {
        List<JSONObject> jdbcList = getClientConfig("rocketmq");
        JSONObject jsonObject = jdbcList.get(0);
        DefaultMQProducer producer = new DefaultMQProducer("test-handyman-group");
        producer.setNamesrvAddr(jsonObject.get("url").toString());
        producer.start();
        clientMap.put(DEFAULT_KEY, producer);
    }
}