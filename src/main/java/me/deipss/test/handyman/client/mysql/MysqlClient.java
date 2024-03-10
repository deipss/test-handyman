package me.deipss.test.handyman.client.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import me.deipss.test.handyman.client.AbstractClient;
import me.deipss.test.handyman.client.ClientResponse;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
@Slf4j
public class MysqlClient extends AbstractClient<MysqlRequest, DataSource, Object> {


    public List<Map<String, Object>> executeQuery(MysqlRequest request) {
        Statement statement = null;
        Connection connection = null;
        try {
            DataSource dataSource = clientMap.get(request.getClientKey());
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request.getSql());
            ResultSetMetaData metaData = resultSet.getMetaData();
            ArrayList<Map<String, Object>> resultList = new ArrayList<>(16);
            while (resultSet.next()) {
                int columnCount = metaData.getColumnCount();
                HashMap<String, Object> map = new HashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    map.put(columnName, resultSet.getObject(columnName));
                }
                resultList.add(map);
            }
            return resultList;
        } catch (SQLException e) {
            log.info("mysql execute error,", e);
        } finally {
            try {
                if (Objects.nonNull(statement)) {
                    statement.close();
                }
                if (Objects.nonNull(connection)) {
                    connection.commit();
                    connection.close();
                }
            } catch (SQLException e) {
                log.info("mysql close resources error", e);
            }
        }
        return null;
    }


    public int executeUpdate(MysqlRequest request) {
        Statement statement = null;
        Connection connection = null;
        try {
            DataSource dataSource = clientMap.get(DEFAULT_KEY);
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            return statement.executeUpdate(request.getSql());
        } catch (SQLException e) {
            log.info("mysql execute error,", e);
        } finally {
            try {
                if (Objects.nonNull(statement)) {
                    statement.close();
                }
                if (Objects.nonNull(connection)) {
                    connection.commit();
                    connection.close();
                }
            } catch (SQLException e) {
                log.info("mysql close resources error", e);
            }
        }
        return -1;
    }


    @Override
    public ClientResponse<Object> execute(MysqlRequest request) {
        return null;
    }

    @Override
    public void initClient() {
        clientMap = new HashMap<>();
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setPassword("");
        mysqlDataSource.setUser("");
        mysqlDataSource.setUrl("");
        clientMap.put(DEFAULT_KEY, mysqlDataSource);
    }
}
