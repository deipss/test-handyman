package me.deipss.test.handyman.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteContext {

    private Map<String,Object> globalMap;

    private Map<String,Object> nextStepMap;

    private String nextStep;

    private List<String> logs;



}
