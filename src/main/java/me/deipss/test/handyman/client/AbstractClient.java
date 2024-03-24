package me.deipss.test.handyman.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.util.FileReadUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * R request
 * T client
 * K return
 */
@Component
@Slf4j
public abstract class AbstractClient<R extends ClientRequest, T, K> implements BaseClient<R, T, K> {

    /**
     * client map
     * key = union key
     * map = client e.g. http client
     */
    protected Map<String, T> clientMap = new HashMap<>(2);


    protected List<JSONObject> getClientConfig(String clientName) {
        log.info("当前的调用类={},hashcode={}",this.getClass().getCanonicalName(),this);
        List<JSONObject>  clientConfigList = JSON.parseArray(FileReadUtil.getResourceFile("client.json"), JSONObject.class);
        return clientConfigList.stream().filter(i -> Objects.equals(clientName, i.get("clientName")))
                .filter(i -> Objects.equals("dev", i.get("env"))).collect(Collectors.toList());
    }

}
