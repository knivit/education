package com.tsoft.demo.channels.pollable;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.tsoft.demo.channels.pollable.QueueChannelTest.Config;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@Slf4j
@SpringBootTest
@EnableIntegration
@ContextConfiguration(classes = Config.class)
public class QueueChannelTest {

    static class Config {
        @Bean
        public QueueChannel inputChannel() {
            return MessageChannels.queue(2).get();
        }

        @Bean
        public IntegrationFlow loggerFlow(PollableChannel inputChannel) {
            return IntegrationFlows
                .from(() -> inputChannel.receive(), c -> c.poller(Pollers.fixedRate(100, TimeUnit.MILLISECONDS)))
                .handle(loggerFlowHandler())
                .get();
        }

        @Bean
        public MessageHandler loggerFlowHandler() {
            return m -> log.info("Processing {}", m);
        }
    }

    @Autowired
    private QueueChannel inputChannel;

    @Test
    public void test() {
        inputChannel.setCountsEnabled(true);

        IntStream.range(0, 5)
            .forEach(v -> {
                inputChannel.send(MessageBuilder
                    .withPayload(v)
                    .build());
            });

        Callable check = Executors.callable(() -> {
            inputChannel.addInterceptor(new ChannelInterceptor() {
            });
        });

        assertThat(inputChannel.receiveCount()).isEqualTo(0);
    }
}
