package com.tsoft.demo.channels.publishsubscribe;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;

import static com.tsoft.demo.channels.publishsubscribe.DirectChannelTest.Config;

@Slf4j
@SpringBootTest
@EnableIntegration
@ContextConfiguration(classes = Config.class)
public class DirectChannelTest {

    @Configuration
    static class Config {
        @Bean
        public SubscribableChannel inputChannel() {
            return MessageChannels.direct().get();
        }

        @Bean
        public IntegrationFlow loggerFlow() {
            return IntegrationFlows
                .from(inputChannel())
                .log()
                .handle(loggerFlowHandler())
                .get();
        }

        @Bean
        public MessageHandler loggerFlowHandler() {
            return m -> log.info("Processing {}", m);
        }
    }

    @Autowired
    private MessageChannel inputChannel;

    @Test
    void test1() {
        inputChannel.send(MessageBuilder
            .withPayload("test")
            .build());
    }
}
