package com.sqs.poc.aws.sqs.queue;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sqs.poc.aws.sqs.dto.Message;
import com.sqs.poc.aws.sqs.service.SqsService;

@Service
public class QueueService {

  @Autowired
  private SqsService sqsService;
  private static final String QUEUE_NAME = "TestQueue.fifo";

  public void send(String message) {
    Message queueMessage = new Message();
    queueMessage.setId(UUID.randomUUID().toString().replace("-", ""));
    queueMessage.setMessage(message);
    sqsService.sendMessage(QUEUE_NAME, queueMessage);
  }
}
