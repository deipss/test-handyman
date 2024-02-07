package me.deipss.test.handyman.client.jdbc;

import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import me.deipss.test.handyman.util.SqlUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public void initClient() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
        dataSource.setUsername("guest_user");
        dataSource.setPassword("guest_password");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        clientMap.put(DEFAULT_KEY, jdbcTemplate);
    }


}
