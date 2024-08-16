package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaProducerParams {

    @JsonSetter(nulls = Nulls.SKIP)
    private String url = "https://jsonplaceholder.typicode.com/posts";

    @JsonSetter(nulls = Nulls.SKIP)
    private String method = "POST";

    @JsonSetter(nulls = Nulls.SKIP)
    private String body_format = "json";

    @JsonSetter(nulls = Nulls.SKIP)
    private Map<String, String> headers = Map.of(
            "Content-Type", "application/json"
    );

    @JsonSetter(nulls = Nulls.SKIP)
    private boolean return_content = true;

    @JsonSetter(nulls = Nulls.SKIP)
    List<Integer> status_code = Arrays.asList(200, 201);


    private Map<String, String> body;
}
