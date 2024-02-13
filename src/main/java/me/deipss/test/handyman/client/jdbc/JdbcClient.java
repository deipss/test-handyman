package me.deipss.test.handyman.client.jdbc;

import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.util.SqlUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JdbcClient extends AbstractClient<JdbcRequest, JdbcTemplate, Object> {

    @Override
    public ClientResponse<Object> execute(JdbcRequest request) {
        return null;
    }

    public ClientResponse<Integer> update(JdbcRequest request) {
        JdbcTemplate jdbcTemplate = clientMap.get(DEFAULT_KEY);
        Pair<String, List<Object>> insert = SqlUtil.insert(request.getSql(), request.getJsonObject());
        int update = jdbcTemplate.update(insert.getKey(), insert.getValue());
        return ClientResponse.<Integer>builder().data(update).build();
    }

    public ClientResponse<List<Map<String, Object>>> query(JdbcRequest request) {
        JdbcTemplate jdbcTemplate = clientMap.get(DEFAULT_KEY);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(request.getSql());
        return ClientResponse.<List<Map<String, Object>>>builder().data(maps).build();
    }

    @Override
    public void initClient() {
        if (clientMap==null) {
            clientMap  = new HashMap<>(1);
        }
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.0.2:3306/inspector");
        dataSource.setUsername("root");
        dataSource.setPassword("mysql_Xh7Z62");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        clientMap.put(DEFAULT_KEY, jdbcTemplate);
    }


}
