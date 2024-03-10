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

    public boolean successHttp(){
        return code==200;
    }

    public boolean success(){
        return code==1;
    }

    public boolean fail(){
        return code==-1;
    }

    


}
