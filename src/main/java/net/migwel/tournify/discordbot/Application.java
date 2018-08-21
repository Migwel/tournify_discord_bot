package net.migwel.tournify.discordbot;

import net.migwel.tournify.discordbot.listener.SubscriptionListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(DiscordProperties.class)
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    DiscordApi discordApi(DiscordProperties properties, SubscriptionListener subscriptionListener) {
        String token = properties.getToken();
        DiscordApi discordApi = new DiscordApiBuilder().setToken(token).login().join();
        discordApi.addMessageCreateListener(subscriptionListener);
        return discordApi;
    }
}
