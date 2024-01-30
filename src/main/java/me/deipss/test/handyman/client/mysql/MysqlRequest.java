package me.deipss.test.handyman.client.mysql;

import me.deipss.test.handyman.client.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysqlRequest extends ClientRequest {
    private String driverClassName;
    private String password;
    private String username;
    private String url;
    private String sql;
}
