package com.tsoft.demo.ignitemessage;

import com.tsoft.demo.ignitemessage.listeners.MessageLogger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;
import java.util.stream.IntStream;
import static com.tsoft.demo.ignitemessage.AppNode.Config;

@Slf4j
@SpringBootTest
@ContextConfiguration(classes = Config.class)
public class AppNode {

    @Autowired
    private MessageServer server1;

    @Autowired
    private MessageServer server2;

    @Autowired
    private MessageCluster cluster;

    @Autowired
    private MessageLogger messageLogger;

    @Test
    public void test() {
        IgniteMessaging logChannel = cluster.getChannels().get(0);
        logChannel.remoteListen(Topic.TOPIC_1, messageLogger);
        /*logChannel.remoteListen(Topic.TOPIC_1, new IgniteBiPredicate<UUID, Object>() {
            @SpringApplicationContextResource
            private ApplicationContext applicationContext;

            @Override
            public boolean apply(UUID node, Object msg) {
                MessageLogger logger = applicationContext.getBean(MessageLogger.class);
                logger.apply(node, (String)msg);
                // log.info("Node {}, message {}", node, msg);
                //AppNode.this.doWork(node, msg);
                return true;
            }
        });*/


        IntStream.range(0, 10).forEach(n -> logChannel.sendOrdered(Topic.TOPIC_1, n, 0));
    }

    @Getter
    static class MessageServer {
        private String serverName;
        private List<MessageListener> listeners;

        public MessageServer(String serverName, MessageListener ... listeners) {
            this.serverName = serverName;
            this.listeners = Arrays.asList(listeners);
        }
    }

    @Getter
    static class MessageCluster {
        private final List<IgniteMessaging> channels = new ArrayList<>();

        public MessageCluster addChannel(IgniteMessaging channel) {
            channels.add(channel);
            return this;
        }
    }

    @Configuration
    static class Config {

        @Bean
        public MessageListener<Integer> messageLogger() {
            return new MessageLogger();
        }

        @Bean
        public MessageServer server1() {
            return new MessageServer("server1", messageLogger());
        }

        @Bean
        public MessageServer server2() {
            return new MessageServer("server2", messageLogger());
        }

        @Bean
        public MessageCluster igniteMessaging(List<MessageServer> servers) {
            MessageCluster cluster = new MessageCluster();

            servers.forEach(s -> {
                IgniteConfiguration cfg = new IgniteConfiguration();
                cfg.setIgniteInstanceName(s.getServerName());

                Ignite ignite = Ignition.getOrStart(cfg);
                IgniteMessaging igniteMessaging = ignite.message(ignite.cluster().forRemotes());

               /* s.getListeners()
                    .forEach(l -> igniteMessaging.remoteListen(l.getTopic(), (node, msg) -> {
                       // MessageListener lis = SpringContextAccessor.getBean(l.getClass());
                        //lis.apply(node, msg);
                        log.info("Got a message {}", msg);
                        return true;
                    }
                    ));*/

                //igniteMessaging.remoteListen(Topic.TOPIC_1, messageLogger());
/*
                igniteMessaging.remoteListen(Topic.TOPIC_1, new IgniteBiPredicate<UUID, Object>() {
                   // @SpringApplicationContextResource
                    //private ApplicationContext applicationContext;

                    @Override
                    public boolean apply(UUID node, Object msg) {
                        //MessageLogger logger = applicationContext.getBean(MessageLogger.class);
                        //logger.apply(node, (String)msg);
                         log.info("Node {}, message {}", node, msg);
                        //AppNode.this.doWork(node, msg);
                        return true;
                    }
                });
*/
                cluster.addChannel(igniteMessaging);
            });

            return cluster;
        }
    }
}
