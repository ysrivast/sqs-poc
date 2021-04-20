package com.sqs.poc.aws.sqs.listener;

import java.util.concurrent.ExecutionException;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class QoueueListener {

  @Async
  @SqsListener(value = "TestQueue.fifo", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
  public void listen(Acknowledgment acknowledgment, String message, String messageId) {
    log.info("Queue One message received message : {}, messageId: {} ", message, messageId);
    try {
      acknowledgment.acknowledge().get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
}
