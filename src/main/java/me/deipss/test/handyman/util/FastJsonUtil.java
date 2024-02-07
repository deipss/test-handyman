package me.deipss.test.handyman.util;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class FastJsonUtil {

    /**
     * @param o     操作对象
     * @param filed 被忽略字段
     */
    public static JSONArray removeFiled(JSONObject o, String filed) {
        JSONArray removeList = new JSONArray();
        Queue<JSONObject> queue = new LinkedList<>();
        queue.add(o);
        while (!queue.isEmpty()) {
            JSONObject cur = queue.remove();
            Object remove = cur.remove(filed);
            IfUtil.trueDo(Objects.nonNull(remove),()->removeList.add(remove));
            for (Map.Entry<String, Object> entry : cur.entrySet()) {
                Object v = entry.getValue();
                // 是JSONObject，置入队列
                if (v instanceof JSONObject) {
                    queue.add((JSONObject) v);
                }
                // JSONArray，将数组中的JSONObject对象置入队列
                if (v instanceof JSONArray) {
                    JSONArray arr = (JSONArray) v;
                    for (Object a : arr) {
                        if (a instanceof JSONObject) {
                            queue.add((JSONObject) a);
                        }
                    }
                }
            }
        }
        return removeList;
    }

}
