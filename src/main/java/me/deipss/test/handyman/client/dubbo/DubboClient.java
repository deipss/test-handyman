package me.deipss.test.handyman.client.dubbo;

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
import java.util.HashMap;

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
        if (null == clientMap) {
            clientMap = new HashMap<>();
        }
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
        applicationConfig.setName("dubbo-admin");
        applicationConfig.setRegistry(registryConfig);
    }


    private RegistryConfig buildRegistryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setAddress("zookeeper://192.168.0.2:2181");
        return config;
    }

    @SneakyThrows
    public void initClient(DubboRequest request) {
        if (null == clientMap) {
            clientMap = new HashMap<>();
        }
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface(request.getInterfaceName());
        reference.setApplication(applicationConfig);
        reference.setGeneric(true);
        reference.setSticky(false);
        reference.setTimeout(10000);
        clientMap.put(DEFAULT_KEY, reference.get());
    }


}
