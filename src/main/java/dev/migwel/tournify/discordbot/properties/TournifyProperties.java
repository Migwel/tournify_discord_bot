package dev.migwel.tournify.discordbot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tournify")
public class TournifyProperties {
    private String tournifyUrl;

    private String callbackUrl;

    public String getTournifyUrl() {
        return tournifyUrl;
    }

    public void setTournifyUrl(String tournifyUrl) {
        this.tournifyUrl = tournifyUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
