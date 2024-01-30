package me.deipss.test.handyman.client.rocketmq;

import me.deipss.test.handyman.client.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RocketmqRequest extends ClientRequest {
        private String namespace;
        private String group;
        private String msg;
        private String topic;
        private String tags;
}
