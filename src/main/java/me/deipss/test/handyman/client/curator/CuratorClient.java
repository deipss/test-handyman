package me.deipss.test.handyman.client.curator;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.api.server.ServiceDefinition;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.BaseClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CuratorClient implements BaseClient<CuratorRequest> {

    @Override
    public Object execute(CuratorRequest request) {
        CuratorFramework client = getClient(request);
        try {
            client.start();
            client.blockUntilConnected();
            return JSON.parseObject(getData(client, request.getPath()), ServiceDefinition.class);
        } catch (Exception e) {
            log.error("curator error", e);
        } finally {
            client.close();
        }
        return null;
    }

    public CuratorFramework getClient(CuratorRequest request) {
        return CuratorFrameworkFactory.builder()
                .connectString(request.getNamespaceAddress())
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(60 * 1000) //连接超时时间，默认15秒
                .sessionTimeoutMs(60 * 1000) //会话超时时间，默认60秒
                .build();
    }

    public void create(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        client.create().creatingParentsIfNeeded().forPath(path, payload);
    }

    public void createEphemeral(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    public String createEphemeralSequential(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        return client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }

    public void setData(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        client.setData().forPath(path, payload);
    }

    public void delete(final CuratorFramework client, final String path) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    public void guaranteedDelete(final CuratorFramework client, final String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

    public String getData(final CuratorFramework client, final String path) throws Exception {
        return new String(client.getData().forPath(path));
    }


    public List<String> getChildren(final CuratorFramework client, final String path) throws Exception {
        return client.getChildren().forPath(path);
    }


}
