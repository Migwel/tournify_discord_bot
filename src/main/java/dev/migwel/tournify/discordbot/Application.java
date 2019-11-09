package dev.migwel.tournify.discordbot;

import dev.migwel.tournify.discordbot.listener.AbstractListener;
import dev.migwel.tournify.discordbot.properties.DiscordProperties;
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
    DiscordApi discordApi(DiscordProperties properties, AbstractListener abstractListener) {
        String token = properties.getToken();
        DiscordApi discordApi = new DiscordApiBuilder().setToken(token).login().join();
        discordApi.addMessageCreateListener(abstractListener);
        return discordApi;
    }
}
