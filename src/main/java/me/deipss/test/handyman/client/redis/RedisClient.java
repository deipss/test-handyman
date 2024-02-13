package me.deipss.test.handyman.client.redis;

import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RedisClient extends AbstractClient<RedisRequest, JedisConnectionFactory, Object> {


    @Override
    public ClientResponse<Object> execute(RedisRequest request) {
        JedisConnectionFactory jedisConnectionFactory = null;
        try {
            jedisConnectionFactory = clientMap.get(DEFAULT_KEY);
            Pair<String, byte[][]> commandPair = buildCommand(request.getCommand());
            Object execute = jedisConnectionFactory.getConnection().execute(commandPair.getKey(), commandPair.getValue());
            assert execute != null;
            if (execute.getClass().isPrimitive()) {
                return ClientResponse.<Object>builder().data(new String((byte[]) execute)).build();

            }
            if (execute.getClass().getName().endsWith("List")) {
                ArrayList<String> objects = new ArrayList<>(((Collection<?>) execute).size());
                ((Collection<?>) execute).forEach(o -> {
                    objects.add(new String((byte[]) o));
                });
                return ClientResponse.<Object>builder().data(objects).build();
            }
            if (execute.getClass().getName().endsWith("Map")) {
                Map<String, String> map = new HashMap<>(((Map<?, ?>) execute).size());
                ((Map<?, ?>) execute).forEach((key, value) -> map.put(new String((byte[]) key), new String((byte[]) value)));
                return ClientResponse.<Object>builder().data(execute).build();
            }
        } catch (Exception e) {
            log.error("redis operate error", e);
        } finally {
            if (null != jedisConnectionFactory) {
                jedisConnectionFactory.getConnection().close();
            }
        }
        return null;
    }

    @Override
    public void initClient() {
        if (Objects.isNull(clientMap)) {
            clientMap = new HashMap<>();
        }

        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName("192.168.0.2");
        jedisConFactory.setPassword("jhkdjhkjdhsIUTYURTU_T2RPWr");
        jedisConFactory.setDatabase(0);
        jedisConFactory.setPort(6379);

        jedisConFactory.afterPropertiesSet();
        clientMap.put(DEFAULT_KEY, jedisConFactory);
    }

    private JedisConnectionFactory jedisConnectionFactory(RedisRequest request) {
        return null;
    }

    private Pair<String, byte[][]> buildCommand(String command) {
        List<String> collect = Arrays.stream(command.trim().split(" ")).filter(i -> !Strings.isBlank(i)).collect(Collectors.toList());
        String preCommand = collect.get(0);
        collect.remove(0);
        byte[][] bytes = collect.stream().map(String::getBytes).collect(Collectors.toList()).toArray(new byte[0][0]);
        return Pair.of(preCommand, bytes);
    }
}
