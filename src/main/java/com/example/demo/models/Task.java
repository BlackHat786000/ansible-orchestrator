package com.example.demo.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "module",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UriTask.class, name = "uri"),
        @JsonSubTypes.Type(value = DebugTask.class, name = "debug"),
        @JsonSubTypes.Type(value = CommandTask.class, name = "command"),
        @JsonSubTypes.Type(value = KafkaProducerTask.class, name = "kafka_producer")
})
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Task {

    @JsonSetter(nulls = Nulls.SKIP)
    private String name = "";
    private String module;
    private String register;

    public abstract Object getModuleParams();
}
