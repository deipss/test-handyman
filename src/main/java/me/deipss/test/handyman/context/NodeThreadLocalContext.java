package me.deipss.test.handyman.context;

import me.deipss.test.handyman.pattern.chain.NodeContext;

public class NodeThreadLocalContext {

    private static ThreadLocal<NodeContext> ttlContext = new TransmittableThreadLocal<>();

}
