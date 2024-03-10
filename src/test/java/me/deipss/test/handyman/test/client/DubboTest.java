package me.deipss.test.handyman.test.client;

import com.alibaba.fastjson.JSON;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.client.dubbo.DubboClient;
import me.deipss.test.handyman.client.dubbo.DubboRequest;
import org.junit.Test;

public class DubboTest {

    @Test
    public void test(){
        DubboClient dubboClient = new DubboClient();
        dubboClient.init();
        DubboRequest dubboRequest = new DubboRequest();
        dubboRequest.setInterfaceName("me.deipss.jvm.sandbox.inspector.debug.api.facade.PaymentFacade");
        dubboRequest.setMethod("payment");
        dubboRequest.setParamNameList(new String[]{"me.deipss.jvm.sandbox.inspector.debug.api.request.PaymentRequest"});
        dubboRequest.setParamObjList(new Object[]{});
        dubboRequest.setUrl("zookeeper://192.168.0.2:2181");
        dubboClient.initClient(dubboRequest);

        ClientResponse<Object> execute = dubboClient.execute(dubboRequest);
        System.out.println(JSON.toJSONString(execute));
    }
}
