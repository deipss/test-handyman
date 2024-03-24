package me.deipss.test.handyman.web.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.business.domain.ExecuteContext;
import me.deipss.test.handyman.context.ProcessContext;
import me.deipss.test.handyman.web.domain.ExecuteRequest;
import me.deipss.test.handyman.web.domain.ExecuteResponse;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/execute")
@Slf4j
public class ExecuteController {

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/all")
    public ExecuteResponse execute(@RequestBody ExecuteRequest request) {
        if (!CollectionUtils.isEmpty(request.getBizMap())) {
            ExecuteContext executeContext = new ExecuteContext();
            executeContext.setGlobalMap(request.getBizMap());
            executeContext.setLogs(Lists.newArrayList());
            ProcessContext.set(executeContext);
        }
        ExecuteResponse executeResponse = new ExecuteResponse();
        try {
            Object bean = applicationContext.getBean(request.getClassName());
            executeResponse.setResult(MethodUtils.invokeMethod(bean, "execute"));
            executeResponse.setExeLogs(ProcessContext.get().getLogs());
        } catch (NoSuchMethodException e) {
            executeResponse.setMsg(request.getClassName() + "类不存在");
        } catch (IllegalAccessException e) {
            executeResponse.setMsg(request.getClassName() + "类的方法，反射调用时，无访问权限，考虑修改为public");
        } catch (InvocationTargetException e) {
            executeResponse.setMsg(request.getClassName() + "类的目标对象，调用失败");
        } finally {
            ProcessContext.remove();
        }
        return executeResponse;
    }
}
