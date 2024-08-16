package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UriTask extends Task {

    @NotNull
    @Valid
    private UriModuleParams moduleParams;

//    @Override
//    public UriModuleParams getModuleParams() {
//        return moduleParams;
//    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UriModuleParams {

        @NotEmpty(message = "url is a mandatory field")
        private String url;

        @NotEmpty(message = "method is a mandatory field")
        private String method;

        private boolean return_content;

        private Map<String, String> headers;
    }
}
