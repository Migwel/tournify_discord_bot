package dev.migwel.tournify.discordbot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("discord")
public class DiscordProperties {

    private String token;

    private Long botId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getBotId() {
        return botId;
    }

    public void setBotId(Long botId) {
        this.botId = botId;
    }
}
