package me.deipss.test.handyman.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteRequest {

    private String className;

    private Map<String,Object> bizMap;


}
