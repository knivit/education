package com.tsoft.demo.ignitemessage.listeners;

import com.tsoft.demo.ignitemessage.MessageListener;
import com.tsoft.demo.ignitemessage.Topic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@NoArgsConstructor
public class MessageLogger implements MessageListener<Integer> {

    public final Topic topic = Topic.TOPIC_1;

    @Override
    public boolean apply(UUID node, Integer msg) {
        log.debug("Received msg = {} from node = {}", node, msg);
        return true;
    }
}
