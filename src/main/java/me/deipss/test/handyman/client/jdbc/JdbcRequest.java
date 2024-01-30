package me.deipss.test.handyman.client.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.deipss.test.handyman.client.ClientRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcRequest extends ClientRequest {

    private String path;

    private String namespaceAddress;
}
