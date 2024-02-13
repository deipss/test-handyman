package me.deipss.test.handyman.client.dubbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.deipss.test.handyman.client.ClientRequest;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DubboRequest extends ClientRequest {

    private String interfaceName;

    private String version;

    private String method;


    private String url;

    private Map<String,String> rpcContext;

    private String[] paramNameList;


    private Object[] paramObjList;
}
