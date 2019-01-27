package net.migwel.tournify.discordbot.request;

import net.migwel.tournify.discordbot.data.Updates;

public class NotificationRequest {

    private Updates updates;

    public NotificationRequest() {
    }

    public NotificationRequest(Updates updates) {
        this.updates = updates;
    }

    public Updates getUpdates() {
        return updates;
    }

    public void setUpdates(Updates updates) {
        this.updates = updates;
    }
}
