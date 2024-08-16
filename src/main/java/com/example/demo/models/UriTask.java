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
public class UriTask extends Task {

    @NotNull
    @Valid
    private UriModuleParams moduleParams;

//    @Override
//    public UriModuleParams getModuleParams() {
//        return moduleParams;
//    }
}
