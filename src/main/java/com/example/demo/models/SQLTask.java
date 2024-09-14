package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SQLTask extends Task {

    @NotNull
    @Valid
    private SQLModuleParams moduleParams;

    @Override
    public String getDelegateTo() {
        return "localhost";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SQLModuleParams {

        @NotEmpty(message = "login_host is a mandatory field")
        private String login_host;

        @NotEmpty(message = "login_user is a mandatory field")
        private String login_user;

        @NotEmpty(message = "login_password is a mandatory field")
        private String login_password;

        @NotEmpty(message = "db is a mandatory field")
        private String db;

        @NotEmpty(message = "script is a mandatory field")
        private String script;
    }
}
