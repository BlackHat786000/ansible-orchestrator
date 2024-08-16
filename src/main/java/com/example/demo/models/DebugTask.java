package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebugTask extends Task {

    @NotNull
    @Valid
    private DebugModuleParams moduleParams;

//    @Override
//    public DebugModuleParams getModuleParams() {
//        return moduleParams;
//    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DebugModuleParams {

        @NotNull(message = "msg cannot be null")
        private String msg;
    }
}
