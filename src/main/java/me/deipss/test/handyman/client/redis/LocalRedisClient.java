package me.deipss.test.handyman.client.redis;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LocalRedisClient extends AbstractClient<RedisRequest, JedisConnectionFactory, Object> {

    protected Map<String, RedisTemplate<String,String>> redisTemplateMap= new HashMap<>();
    @Override
    public ClientResponse<Object> execute(RedisRequest request) {
        JedisConnectionFactory jedisConnectionFactory = null;
        try {
            jedisConnectionFactory = clientMap.get(request.getClientKey());
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
    public String get(RedisRequest request){
        RedisTemplate<String, String> stringStringRedisTemplate = redisTemplateMap.get(request.getKey());
        return stringStringRedisTemplate.opsForValue().get(request.getKey());
    }

    @Override
    @PostConstruct
    public void initClient() {
        if (Objects.isNull(clientMap)) {
            clientMap = new HashMap<>();
        }

        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        List<JSONObject> jdbcList = getClientConfig("redis");
        JSONObject jsonObject = jdbcList.get(0);
        jedisConFactory.setHostName(jsonObject.get("ip").toString());
        jedisConFactory.setPassword(jsonObject.get("password").toString());
        jedisConFactory.setDatabase(Integer.parseInt(jsonObject.get("database").toString()));
        jedisConFactory.setPort(Integer.parseInt(jsonObject.get("port").toString()));
        jedisConFactory.afterPropertiesSet();
        clientMap.put(DEFAULT_KEY, jedisConFactory);

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConFactory);
        template.setKeySerializer(StringRedisSerializer.UTF_8);
        template.setValueSerializer(StringRedisSerializer.UTF_8);
        redisTemplateMap.put(DEFAULT_KEY,template);
    }

    private Pair<String, byte[][]> buildCommand(String command) {
        List<String> collect = Arrays.stream(command.trim().split(" ")).filter(i -> !Strings.isBlank(i)).collect(Collectors.toList());
        String preCommand = collect.get(0);
        collect.remove(0);
        byte[][] bytes = collect.stream().map(String::getBytes).collect(Collectors.toList()).toArray(new byte[0][0]);
        return Pair.of(preCommand, bytes);
    }
}
