package me.deipss.test.handyman.pattern.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 节点结果
 */
@Data
@AllArgsConstructor
@Builder
@ToString
public class NodeResult<R> {
    /**
     * 当前节点是否成功
     */
    private boolean success;

    /**
     * 是否结束链路
     */
    private boolean end;
    /**
     * 下一个结点的UK
     */
    private String nextNodeUk;

    /**
     * 节点执行结果
     */
    private R data;
}
