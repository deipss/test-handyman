package me.deipss.test.handyman.business;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.dubbo.DubboClient;
import me.deipss.test.handyman.client.http.LocalHttpClient;
import me.deipss.test.handyman.client.jdbc.JdbcClient;
import me.deipss.test.handyman.client.redis.LocalRedisClient;
import me.deipss.test.handyman.client.rocketmq.RocketmqClient;
import me.deipss.test.handyman.context.ProcessContext;
import me.deipss.test.handyman.util.FileReadUtil;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public abstract class BaseProcess {

    @Resource
    protected DubboClient dubboClient;
    @Resource
    protected LocalHttpClient httpClient;
    @Resource
    protected JdbcClient jdbcClient;
    @Resource
    protected LocalRedisClient redisClient;
    @Resource
    protected RocketmqClient rocketmqClient
            ;

    protected List<JSONObject> dataList;

    @PostConstruct
    protected void loadDate() {
        dataList = JSON.parseArray(FileReadUtil.getResourceFile("process/" + this.getClass().getSimpleName() + ".json"), JSONObject.class);
    }

    public void execute() {
        JSONObject jsonObject = dataList.get(0);
        if (ProcessContext.get() == null) {
            ProcessContext.init();
        }
        jsonObject.putAll(ProcessContext.get().getGlobalMap());

        List<Method> methodList = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(i -> i.isAnnotationPresent(Order.class))
                .sorted(Comparator.comparingInt(i -> i.getAnnotation(Order.class).value())).collect(Collectors.toList());

        for (Method method : methodList) {
            Parameter[] parameters = method.getParameters();
            Object[] objects = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                if (ProcessContext.get().getGlobalMap().containsKey(parameters[i].getName())) {
                    objects[i] = ProcessContext.get().getGlobalMap().get(parameters[i].getName());
                }
            }
            String logFormat = String.format("%s类的方法%s，执行参数=%s,结果=", this.getClass().getName(), method.getName(), JSON.toJSONString(objects));
            log.info(logFormat);
            try {
                Object o = MethodUtils.invokeMethod(this, method.getName(), objects);
                logFormat += o.toString();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                logFormat += e.getLocalizedMessage();
                ProcessContext.get().getLogs().add(logFormat);
                log.error("{}发生异常",logFormat,e);
                break;
            }
            ProcessContext.get().getLogs().add(logFormat);
        }

    }


}
