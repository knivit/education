package com.tsoft.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.*;
import org.springframework.integration.history.MessageHistory;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner starter() {
		return (args) -> {
			IntStream.range(0, 5)
				.forEach(v -> {
					Message msg = MessageBuilder
						.withPayload(v)
						.build();

					eventsChannel().send(msg);
				});

			System.exit(0);
		};
	}

	@Bean
	public PollableChannel eventsChannel() {
		return MessageChannels.queue(2).get();
	}

	/*	@Bean
        public SubscribableChannel eventsChannel() {
            Executor executor = Executors.newCachedThreadPool();
            return MessageChannels.executor(executor).get();
        }
    */
	@Bean
	public MessageChannel testChannel() {
		return new PublishSubscribeChannel();
	}

	@Bean
	public IntegrationFlow eventsProcessor(PollableChannel eventsChannel) {
		return IntegrationFlows
				//.from(() -> eventsChannel.receive(), c -> c.poller(Pollers.fixedRate(100)))
				.from(() -> eventsChannel.receive(), c -> c.poller(Pollers.fixedRate(100, TimeUnit.MILLISECONDS)))
				//.from(eventsChannel)
				.channel(testChannel())
				.enrich(c -> c.header("channel", 1))
				.handle(workHandler())
				.get();
	}

	@Bean
	public IntegrationFlow interceptorFlow(MessageChannel testChannel) {
		return IntegrationFlows
				.from(testChannel)
				.enrich(c -> c.header("channel", 2))
				//.handle(workHandler())
				.log()
				.get();
	}

	@Bean
	public MessageHandler workHandler() {
		return m -> {
			log.info("Got a message {}, start processing", m);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("Processing ended");
		};
	}
}
