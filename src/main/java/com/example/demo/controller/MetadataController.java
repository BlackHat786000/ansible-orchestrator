package com.example.demo.controller;

import com.example.demo.models.Metadata;
import com.example.demo.models.ProvisionRequest;
import com.example.demo.repository.MetadataRepository;
import com.example.demo.service.MetadataService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("orchestrator")
@RestController
@Validated
public class MetadataController {

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private MetadataService metadataService;

    @PostMapping("/metadata/create")
    public ResponseEntity<String> saveMetadata(@Valid @RequestBody Metadata payload) {
        log.info("Metadata Payload :: {}", payload);
        this.metadataRepository.save(payload);
        return ResponseEntity.ok("Metadata saved in mongoDB successfully");
    }

    @PostMapping("/trigger/matrix")
    public ResponseEntity<String> triggerMatrixWorkflow(@Valid @RequestBody ProvisionRequest payload) {
        String status = this.metadataService.triggerMatrixWorkflow(payload);
        return ResponseEntity.ok(status);
    }
}
