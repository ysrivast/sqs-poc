package com.sqs.poc.aws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.sqs.poc.aws.sqs.queue.QueueService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SqsController {

  @Autowired
  private QueueService queueService;

  @GetMapping("/send/{message}")
  public ResponseEntity<Void> sendSqsOne(@PathVariable String message) {
    log.info("message send request : {}", message);
    queueService.send(message);
    return ResponseEntity.ok().build();
  }

}
