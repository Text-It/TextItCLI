package com.TextIt.model.Message;


public class Messages {
    private final String sender;
    private final String receiver;
    private final String message;
    private final String sentAt;

    public Messages(String sender, String receiver, String message, String sentAt) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sentAt = sentAt;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public String getSentAt() {
        return sentAt;
    }
}
