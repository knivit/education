package com.tsoft.demo.ignitemessage;

import lombok.Getter;
import org.apache.ignite.IgniteMessaging;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageCluster {
    private final List<IgniteMessaging> channels = new ArrayList<>();

    public MessageCluster addChannel(IgniteMessaging channel) {
        channels.add(channel);
        return this;
    }
}
