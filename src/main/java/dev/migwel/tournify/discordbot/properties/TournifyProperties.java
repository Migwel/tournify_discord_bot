package dev.migwel.tournify.discordbot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties("tournify")
@PropertySource("classpath:tournify.properties")
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
