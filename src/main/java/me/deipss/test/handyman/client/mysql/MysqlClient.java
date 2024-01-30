package me.deipss.test.handyman.client.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import me.deipss.test.handyman.client.BaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
@Slf4j
public class MysqlClient implements BaseClient<MysqlRequest> {

    @Override
    public Object execute(MysqlRequest request) {
        if(request.getSql().toLowerCase().trim().startsWith("select")){
            return executeQuery(request);
        }
        return executeUpdate(request);
    }


    public List<Map<String, Object>> executeQuery(MysqlRequest request) {
        Statement statement = null;
        Connection connection = null;
        try {
            DataSource dataSource = buildDataSource(request);
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
            DataSource dataSource = buildDataSource(request);
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


    private DataSource buildDataSource(MysqlRequest property) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setPassword(property.getPassword());
        mysqlDataSource.setUser(property.getUsername());
        mysqlDataSource.setUrl(property.getUrl());
        return mysqlDataSource;
    }


}
