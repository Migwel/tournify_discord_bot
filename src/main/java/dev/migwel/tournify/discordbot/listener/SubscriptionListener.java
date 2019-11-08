package dev.migwel.tournify.discordbot.listener;

import dev.migwel.tournify.discordbot.messagewriter.MessageWriter;
import dev.migwel.tournify.discordbot.messagewriter.MessageWriterFactory;
import dev.migwel.tournify.discordbot.service.SubscriptionService;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionListener {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionListener.class);

    private SubscriptionService subscriptionService;

    private MessageWriterFactory messageWriterFactory;

    public SubscriptionListener(SubscriptionService subscriptionService, MessageWriterFactory messageWriterFactory) {
        this.subscriptionService = subscriptionService;
        this.messageWriterFactory = messageWriterFactory;
    }

    public void action(AbstractListener.ActionType actionType, Channel channel, String url, String playerTag) {
        String message = null;
        switch (actionType) {
            case Subscribe:
                subscriptionService.addSubscription(channel.getId(), url, playerTag);
                message = subscribeMessage(url, playerTag);
                break;
            case Unsubscribe:
                subscriptionService.deleteSubscription(channel.getId(), url);
                message = unsubscribeMessage(url, playerTag);
                break;
        }

        TextChannel textChannel = channel.asTextChannel().orElseThrow(() -> new IllegalArgumentException("Wrong channel"));
        MessageWriter messageWriter = messageWriterFactory.getMessageWriter(textChannel, message);
        messageWriter.write();
    }

    private String subscribeMessage(String url, String playerTag) {
        String message = "Successfully subscribed to "+ url;
        if(playerTag == null || playerTag.isEmpty()) {
            return message;
        }
        return message + " for player "+ playerTag;
    }


    private String unsubscribeMessage(String url, String playerTag) {
        String message = "Successfully unsubscribed to "+ url;
        if(playerTag == null || playerTag.isEmpty()) {
            return message;
        }
        return message + " for player "+ playerTag;
    }
}
