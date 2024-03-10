package me.deipss.test.handyman.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    private String username;

    private String password;

    private String IP;

    private int port;

    private String clientKey="default";
}
