package net.migwel.tournify.discordbot.request;

public class ParticipantsRequest {

    private String url;

    public ParticipantsRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
