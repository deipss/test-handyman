package me.deipss.test.handyman.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequest {

    private String clientName;

    private String username;

    private String password;

    private String IP;

    private int port;

    private String clientKey="default";

    private String env="dev";

    private String url;


}
