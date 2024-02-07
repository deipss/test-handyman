package me.deipss.test.handyman.client.dubbo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class DubboClient extends AbstractClient<DubboRequest, GenericService, Object> {

    @Override
    @SneakyThrows
    public ClientResponse<Object> execute(DubboRequest request) {

        GenericService genericService = clientMap.get(DEFAULT_KEY);

        Object result = genericService.$invoke(request.getMethod(), new String[]{"java.lang.String"}, new Object[]{"world"});
        return ClientResponse.builder().data(result).build();

    }


    @Override
    @SneakyThrows
    public void initClient() {
        if (null == clientMap) {
            clientMap = new HashMap<>();
        }
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("com.xxx.XxxService");
        reference.setVersion("1.0.0");
        reference.setGeneric(true);
        clientMap.put(DEFAULT_KEY, reference.get());
    }


}
