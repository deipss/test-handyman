package me.deipss.test.handyman.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlUtil {
    public  static  String INSERT_SQL  = "insert into #table#  (#columns#) values (#values#) ";

    public static Pair<String, List<Object>> insert(String  tableName, JSONObject obj) {
        String sql = INSERT_SQL.replace("#table#",tableName);
        List<String> columns =new ArrayList<>();
        List<String> placeholders =new ArrayList<>();
        List<Object> values =new ArrayList<>();
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            columns.add("`"+entry.getKey()+"`");
            placeholders.add("?");
            values.add(entry.getValue());
        }
        String join = StringUtils.join(columns, ",");
        sql = sql.replace("#columns#",join);

        join = StringUtils.join(placeholders, ",");
        sql = sql.replace("#values#",join);
        return new MutablePair<>(sql,values);
    }
}
