package com.cjhsoft.tarefa.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjhsoft.tarefa.domain.model.Status;

@RestController
@RequestMapping("/status")
public class StatusController {
	
    @GetMapping
    public Status[] getAllStatuses() {
        return Status.values();
    }
	
}
