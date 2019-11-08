package dev.migwel.tournify.discordbot.listener;

import dev.migwel.tournify.discordbot.ServerException;
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
        switch (actionType) {
            case Subscribe:
                subscribe(channel, url, playerTag);
                break;
            case Unsubscribe:
                unsubscribe(channel, url, playerTag);
                break;
        }
    }

    private void writeMessage(Channel channel, String message) {
        TextChannel textChannel = channel.asTextChannel().orElseThrow(() -> new IllegalArgumentException("Wrong channel"));
        MessageWriter messageWriter = messageWriterFactory.getMessageWriter(textChannel, message);
        messageWriter.write();
    }

    private void unsubscribe(Channel channel, String url, String playerTag) {
        String message;
        try {
            subscriptionService.deleteSubscription(channel.getId(), url);
        } catch (ServerException e) {
            writeMessage(channel, e.getMessage());
            return;
        }
        message = unsubscribeMessage(url, playerTag);
        writeMessage(channel, message);
    }

    private void subscribe(Channel channel, String url, String playerTag) {
        String message;
        try {
            subscriptionService.addSubscription(channel.getId(), url, playerTag);
        } catch (ServerException e) {
            writeMessage(channel, e.getMessage());
            return;
        }
        message = subscribeMessage(url, playerTag);
        writeMessage(channel, message);
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
