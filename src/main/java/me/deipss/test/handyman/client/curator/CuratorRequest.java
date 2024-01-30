package me.deipss.test.handyman.client.curator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.deipss.test.handyman.client.ClientRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuratorRequest extends ClientRequest {

    private String path;

    private String namespaceAddress;
}
