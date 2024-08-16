package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "metadata")
public class Metadata {

    @NotEmpty
    private String componentName;

    @NotEmpty
    @Id
    private String componentCode;

    @NotEmpty
    private String componentType;

    private List<String> dependsOn;

    @NotEmpty
    private List<String> supportedLifeCycleActions;

    private List<String> dataCenters;

    private String invocationStyle;
    private String callbackMechanism;

    @NotEmpty
    private String authenticationMechanism;

    private String authenticationServiceCode;

    @NotNull
    @Valid
    private List<Task> create;
}
