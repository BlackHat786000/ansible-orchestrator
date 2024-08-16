package com.example.demo.service;

import com.example.demo.models.Metadata;
import com.example.demo.models.ProvisionRequest;
import com.example.demo.models.Task;
import com.example.demo.repository.MetadataRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class MetadataService {

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private RestTemplate restTemplate;

    public String triggerMatrixWorkflow(final ProvisionRequest provisionRequest) {
        Optional<Metadata> metadataOptional = this.metadataRepository.findById(provisionRequest.getComponentCode());
        if (metadataOptional.isPresent()) {
            log.info("Metadata found :: {}", metadataOptional.get());
            String playbook = this.constructPlaybook(metadataOptional.get());
            String matrixPayload = this.constructMatrixPayload(playbook, provisionRequest);
            return this.dispatchEvent(matrixPayload);
        } else {
            log.info("Metadata not found for component code: {}", provisionRequest.getComponentCode());
            throw new RuntimeException("Metadata not found for given component code");
        }
    }

    public String constructPlaybook(Metadata metadata) {
        List<Task> taskList = metadata.getCreate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<Map<String, Object>> playbook = new ArrayList<>();

        for (Task task : taskList) {
            Map<String, Object> playNode = new HashMap<>();
            playNode.put("hosts", "localhost");
            playNode.put("gather_facts", false);

            List<Map<String, Object>> tasks = new ArrayList<>();
            Map<String, Object> taskNode = new HashMap<>();
            taskNode.put("name", task.getName());
            taskNode.put(task.getModule(), task.getModuleParams());
            taskNode.put("register", task.getRegister());
            tasks.add(taskNode);

            playNode.put("tasks", tasks);
            playbook.add(playNode);
        }

        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(playbook);
            log.info("Playbook json string :: \n{}", jsonString);
            return jsonString;
        } catch (Exception e) {
            log.error("Error while constructing playbook from metadata", e);
            throw new RuntimeException("Not able to construct playbook from metadata", e);
        }
    }


    public String constructMatrixPayload(String playbook, ProvisionRequest provisionRequest) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> matrixEvent = new HashMap<>();
        matrixEvent.put("event_type", "matrix-poc");

        Map<String, Object> clientPayload = new HashMap<>();

        List<Map<String, Object>> components = new ArrayList<>();

        Map<String, Object> component = new HashMap<>();
        component.put("componentCode", "recruiting");
        component.put("dependsOn", null);

        Map<String, Object> playbookMap = new HashMap<>();
        playbookMap.put("json", playbook);  // Assuming this should be an empty list
        playbookMap.put("inputs", provisionRequest.getInputs());

        component.put("playbook", playbookMap);

        components.add(component);
        clientPayload.put("components", components);
        matrixEvent.put("client_payload", clientPayload);

        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(matrixEvent);
            log.info("matrix workflow event payload :: \n{}", jsonString);
            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to construct trigger matrix workflow payload");
        }
    }

    public String dispatchEvent(String jsonPayload) {
        String url = "https://api.github.com/repos/BlackHat786000/GHADemo/dispatches";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + "");
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return "Request successful";
        } else {
            return "Request failed with status: " + response.getStatusCode();
        }
    }


    /*
    public String constructPlaybook(Metadata metadata) {
        List<Task> taskList = metadata.getCreate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ArrayNode playbook = mapper.createArrayNode();

        for (Task task: taskList) {
            ObjectNode playNode = mapper.createObjectNode();
            playNode.put("hosts", "localhost");
            playNode.put("gather_facts", false);

            ArrayNode tasks = mapper.createArrayNode();
            ObjectNode taskNode = mapper.createObjectNode();
            taskNode.put("name", task.getName());
            taskNode.put(task.getModule(), mapper.valueToTree(task.getModuleParams()));
            taskNode.put("register", task.getRegister());
            tasks.add(taskNode);

            playNode.set("tasks", tasks);

            playbook.add(playNode);
        }

        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(playbook);
            log.info("Playbook json string :: \n{}", jsonString);
            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Not able to construct playbook from metadata");
        }
    }
     */
}
