package me.deipss.test.handyman.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse<T> {
    private T data;
    private int code;
    private String msg;
}
