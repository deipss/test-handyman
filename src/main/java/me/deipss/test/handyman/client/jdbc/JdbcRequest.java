package me.deipss.test.handyman.client.jdbc;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.deipss.test.handyman.client.ClientRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcRequest extends ClientRequest {

    private String sql;
    private String tableName;
    private JSONObject jsonObject;

}
