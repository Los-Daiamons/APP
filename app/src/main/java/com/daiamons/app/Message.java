package com.daiamons.app;

public class Message {

    private long timestamp;
    private String text;

    public Message() {
    }

    public Message(String text, long timestamp) {
        this.text = text;
        this.timestamp = timestamp;

    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

}
