package me.deipss.test.handyman.client.redis;

import me.deipss.test.handyman.client.BaseClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RedisClient implements BaseClient<RedisRequest> {


    @Override
    public Object execute(RedisRequest request) {
        JedisConnectionFactory jedisConnectionFactory = null;
        try {
            jedisConnectionFactory = jedisConnectionFactory(request);
            Pair<String, byte[][]> commandPair = buildCommand(request.getCommand());
            Object execute = jedisConnectionFactory.getConnection().execute(commandPair.getKey(), commandPair.getValue());
            assert execute != null;
            if (execute.getClass().isPrimitive()) {
                return new String((byte[]) execute);
            }
            if (execute.getClass().getName().endsWith("List")) {
                ArrayList<String> objects = new ArrayList<>(((Collection<?>) execute).size());
                ((Collection<?>) execute).forEach(o -> {
                    objects.add(new String((byte[]) o));
                });
                return objects;
            }
            if (execute.getClass().getName().endsWith("Map")) {
                Map<String, String> map = new HashMap<>(((Map<?, ?>) execute).size());
                ((Map<?, ?>) execute).forEach((key, value) -> map.put(new String((byte[]) key), new String((byte[]) value)));
                return map;
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

    private JedisConnectionFactory jedisConnectionFactory(RedisRequest request) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(request.getDatabase());
        redisStandaloneConfiguration.setHostName(request.getHost());
        redisStandaloneConfiguration.setPassword(request.getPassword());
        redisStandaloneConfiguration.setPort(request.getPort());
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        jedisConFactory.afterPropertiesSet();
        return jedisConFactory;
    }

    private Pair<String, byte[][]> buildCommand(String command) {
        List<String> collect = Arrays.stream(command.trim().split(" ")).filter(i -> !Strings.isBlank(i)).collect(Collectors.toList());
        String preCommand = collect.get(0);
        collect.remove(0);
        byte[][] bytes = collect.stream().map(String::getBytes).collect(Collectors.toList()).toArray(new byte[0][0]);
        return Pair.of(preCommand, bytes);
    }

}
