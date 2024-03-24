package me.deipss.test.handyman.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteResponse {

    private List<String> exeLogs;

    private Object result;

    private String msg;
}
