package me.deipss.test.handyman.context;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.alibaba.ttl.TransmittableThreadLocal;
import me.deipss.test.handyman.business.domain.ExecuteContext;

public class ProcessContext {

    private static final ThreadLocal<ExecuteContext> ttlContext = new TransmittableThreadLocal<>();


    public static ExecuteContext get(){
        return ttlContext.get();
    }

    public static void set(ExecuteContext context){
        ttlContext.set(context);
    }

    public static void init(){
        ExecuteContext context = new ExecuteContext ();
        context.setGlobalMap(Maps.newHashMap());
        context.setLogs(Lists.newArrayList());
        ttlContext.set(context);
    }

    public static void remove(){
        if(null!=ttlContext.get()) {
            ttlContext.remove();
        }
    }
}
