package me.deipss.test.handyman.pattern.chain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.pattern.chain.annotation.LocalChainNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@Slf4j
@Component
public class ChainEngine<T> {

    @Resource
    List<ChainNode<T>> nodeList;
    /**
     * 节点映射
     */
    Map<String, ChainNode<T>> nodeMap;
    /**
     * 引擎名称
     */
    private String engineName;

    /**
     * 开始节点
     */
    private ChainNode<T> statedNode;

    public ChainEngine(String engineName) {
        this.engineName = engineName;
    }

    @PostConstruct
    private void init() {
        for (ChainNode<T> node : nodeList) {
            LocalChainNode annotation = node.getClass().getAnnotation(LocalChainNode.class);
            if (annotation.engineName().equals(engineName)) {
                if (annotation.stated()) {
                    statedNode = node;
                }
                nodeMap.putIfAbsent(annotation.nodeUk(), node);
            }
        }
        if (statedNode == null) {
            throw new RuntimeException("未配置起始节点，chain engine name =" + engineName);
        }

    }

    /**
     * @param context 执行的上下文
     * @return 返回结果
     */
    public NodeResult<T> process(NodeContext context) {
        ChainNode<T> currentNode = statedNode;
        NodeResult<T> result = null;
        do {
            result = currentNode.process(context);
            currentNode = nodeMap.get(result.getNextNodeUk());
        } while (!result.isEnd());
        return result;
    }
}
