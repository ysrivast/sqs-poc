package com.sqs.poc.aws.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.SqsClientConfiguration;
import org.springframework.cloud.aws.messaging.config.annotation.SqsConfiguration;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
@Import({SqsClientConfiguration.class, SqsConfiguration.class})
public class AwsConfig {

  @Bean
  public AWSCredentialsProvider awsCredentialsProvider(
      @Value("${aws.sqs.accessKeyId}") String accessKey,
      @Value("${aws.sqs.secretAccessKey}") String secretKey) {
    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
  }

  @Bean
  @Primary
  @Qualifier("amazonSQSAsync")
  public AmazonSQSAsync amazonSQSAsync(@Value("${cloud.aws.region.static}") String region,
      AWSCredentialsProvider awsCredentialsProvider) {
    EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(
        "https://sqs.us-east-2.amazonaws.com/870300444040/TestQueue.fifo", region);
    return AmazonSQSAsyncClientBuilder.standard().withCredentials(awsCredentialsProvider)
        // .withRegion(region)
        .withEndpointConfiguration(endpointConfig).build();
  }

  @Bean
  @Primary
  public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(
      AmazonSQSAsync amazonSqs) {
    SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
    factory.setAmazonSqs(amazonSqs);
    factory.setMaxNumberOfMessages(1);
    factory.setWaitTimeOut(10);
    factory.setQueueMessageHandler(new QueueMessageHandler());
    return factory;
  }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
    return new QueueMessagingTemplate(amazonSQSAsync);
  }
}
