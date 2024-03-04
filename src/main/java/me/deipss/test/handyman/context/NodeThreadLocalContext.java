package me.deipss.test.handyman.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import me.deipss.test.handyman.pattern.chain.NodeContext;

public class NodeThreadLocalContext {

    private static final ThreadLocal<NodeContext> ttlContext = new TransmittableThreadLocal<>();


    public static NodeContext get(){
        return ttlContext.get();
    }

    public static void set(NodeContext context){
        ttlContext.set(context);
    }

    public static void remove(){
        ttlContext.remove();
    }


}
