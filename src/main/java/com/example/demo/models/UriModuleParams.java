package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UriModuleParams {

    @NotEmpty(message = "url is a mandatory field")
    private String url;

    @NotEmpty(message = "method is a mandatory field")
    private String method;

    private boolean return_content;

    private Map<String, String> headers;
}
