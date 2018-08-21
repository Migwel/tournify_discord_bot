package net.migwel.tournify.discordbot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("discord")
public class DiscordProperties {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
