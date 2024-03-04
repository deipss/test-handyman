package me.deipss.test.handyman.client.http;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HttpResult {

    private int httpStatus;

    private boolean success;




    private String data;

}
