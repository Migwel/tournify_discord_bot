package net.migwel.tournify.discordbot.request;

public class SubscriptionRequest {

    private String tournamentUrl;
    private String callbackUrl;

    public SubscriptionRequest(String tournamentUrl, String callbackUrl) {
        this.tournamentUrl = tournamentUrl;
        this.callbackUrl = callbackUrl;
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
