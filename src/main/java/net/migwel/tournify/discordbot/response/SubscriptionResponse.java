package net.migwel.tournify.discordbot.response;

public class SubscriptionResponse {

    private String id;
    private String tournamentUrl;
    private String callbackUrl;

    public SubscriptionResponse() {
    }

    public SubscriptionResponse(String id, String tournamentUrl, String callbackUrl) {
        this.id = id;
        this.tournamentUrl = tournamentUrl;
        this.callbackUrl = callbackUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTournamentUrl() {
        return tournamentUrl;
    }

    public void setTournamentUrl(String tournamentUrl) {
        this.tournamentUrl = tournamentUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}