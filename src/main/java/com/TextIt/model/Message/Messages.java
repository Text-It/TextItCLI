package com.TextIt.model.Message;


public class Messages {
    private String sender;
    private String receiver;
    private String message;
    private String sentAt;

    public Messages(String sender, String receiver, String message, String sentAt) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sentAt = sentAt;
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getMessage() { return message; }
    public String getSentAt() { return sentAt; }
}
