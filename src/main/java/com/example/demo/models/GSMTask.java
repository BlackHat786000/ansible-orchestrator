package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GSMTask extends Task {

    @NotNull
    @Valid
    private GSMModuleParams moduleParams;

    @Override
    public String getDelegateTo() {
        return "localhost";
    }

    @Override
    public Boolean getNoLog() {
        return Boolean.TRUE;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GSMModuleParams {

        @JsonSetter(nulls = Nulls.SKIP)
        private String secret_id = "dev_secret_id";

        @JsonSetter(nulls = Nulls.SKIP)
        private String project_id = "dev_project_id";
    }
}
