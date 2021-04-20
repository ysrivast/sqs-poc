package com.sqs.poc.aws.sqs.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.stereotype.Service;
import com.sqs.poc.aws.sqs.dto.Message;

@Service
public class SqsService {

  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;

  public void sendMessage(String queueName, Message message) {
    Map<String, Object> headers = new HashMap<>();
    headers.put(SqsMessageHeaders.SQS_GROUP_ID_HEADER,
        UUID.randomUUID().toString().replace("-", ""));
    headers.put(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER,
        message.getId().concat("-").concat(UUID.randomUUID().toString().replace("-", "")));
    queueMessagingTemplate.convertAndSend(queueName, message, headers);
  }
}
