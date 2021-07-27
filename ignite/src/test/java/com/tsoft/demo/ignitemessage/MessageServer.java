package com.tsoft.demo.ignitemessage;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class MessageServer {
    private String serverName;
    private List<MessageListener> listeners;

    public MessageServer(String serverName, MessageListener ... listeners) {
        this.serverName = serverName;
        this.listeners = Arrays.asList(listeners);
    }
}
