package me.deipss.test.handyman.client.jdbc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.util.SqlUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JdbcClient extends AbstractClient<JdbcRequest, JdbcTemplate, Object> {

    @Override
    public ClientResponse<Object> execute(JdbcRequest request) {
        JdbcTemplate jdbcTemplate = clientMap.get(request.getClientKey());
        jdbcTemplate.execute(request.getSql());
        return ClientResponse.builder().msg(request.getSql()).build();
    }

    public ClientResponse<Integer> update(JdbcRequest request) {
        JdbcTemplate jdbcTemplate = clientMap.get(request.getClientKey());
        Pair<String, List<Object>> insert = SqlUtil.insert(request.getSql(), request.getJsonObject());
        int update = jdbcTemplate.update(insert.getKey(), insert.getValue());
        return ClientResponse.<Integer>builder().data(update).build();
    }

    public ClientResponse<List<Map<String, Object>>> query(JdbcRequest request) {
        JdbcTemplate jdbcTemplate = clientMap.get(request.getClientKey());
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(request.getSql());
        return ClientResponse.<List<Map<String, Object>>>builder().data(maps).build();
    }

    @Override
    @PostConstruct
    public void initClient() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        List<JSONObject> jdbcList = getClientConfig("jdbc");
        JSONObject jsonObject = jdbcList.get(0);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(jsonObject.get("url").toString());
        dataSource.setUsername(jsonObject.get("username").toString());
        dataSource.setPassword(jsonObject.get("password").toString());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        clientMap.put(DEFAULT_KEY, jdbcTemplate);
    }


}
