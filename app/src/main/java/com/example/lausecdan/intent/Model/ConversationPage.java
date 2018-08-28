package com.example.lausecdan.intent.Model;

public class ConversationPage {
    public boolean seen;
    public long timestamp;

    public ConversationPage(){

    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ConversationPage(boolean seen, long timestamp) {
        this.seen = seen;
        this.timestamp = timestamp;
    }
}
