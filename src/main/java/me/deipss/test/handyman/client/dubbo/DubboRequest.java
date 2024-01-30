package me.deipss.test.handyman.client.dubbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.deipss.test.handyman.client.ClientRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DubboRequest extends ClientRequest {

    private String interfaceName;

    private String version;

    private String method;
}
