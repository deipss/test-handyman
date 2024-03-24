package me.deipss.test.handyman.client.dubbo;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class DubboClient extends AbstractClient<DubboRequest, GenericService, Object> {

    private ApplicationConfig applicationConfig;


    @Override
    @SneakyThrows
    public ClientResponse<Object> execute(DubboRequest request) {
        GenericService genericService = clientMap.get(request.getClientKey());
        Object result = genericService.$invoke(request.getMethod(), request.getParamNameList(), request.getParamObjList());
        return ClientResponse.builder().data(result).build();
    }


    @Override
    @SneakyThrows
    public void initClient() {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("com.xxx.XxxService");
        reference.setVersion("1.0.0");
        reference.setGeneric(true);
        clientMap.put(DEFAULT_KEY, reference.get());
    }

    @PostConstruct
    public void init() {
        RegistryConfig registryConfig = buildRegistryConfig();
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-handyman");
        applicationConfig.setRegistry(registryConfig);
    }


    private RegistryConfig buildRegistryConfig() {
        RegistryConfig config = new RegistryConfig();
        List<JSONObject> dubbo = getClientConfig("dubbo");
        config.setAddress(dubbo.get(0).get("url").toString());
        return config;
    }

    @SneakyThrows
    public void initClient(DubboRequest request) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface(request.getInterfaceName());
        reference.setApplication(applicationConfig);
        reference.setGeneric(true);
        reference.setSticky(false);
        reference.setTimeout(10000);
        clientMap.put(DEFAULT_KEY, reference.get());
    }
}
