package dev.migwel.tournify.discordbot.controller;

import dev.migwel.tournify.communication.commons.Update;
import dev.migwel.tournify.communication.request.NotificationRequest;
import dev.migwel.tournify.communication.response.NotificationResponse;
import dev.migwel.tournify.discordbot.message.GenericMessage;
import dev.migwel.tournify.discordbot.message.Message;
import dev.migwel.tournify.discordbot.store.SubscriptionRepository;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.CheckForNull;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private DiscordApi discordApi;

    private SubscriptionRepository subscriptionRepository;

    public NotificationController(DiscordApi discordApi, SubscriptionRepository subscriptionRepository) {
        this.discordApi = discordApi;
        this.subscriptionRepository = subscriptionRepository;
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.POST)
    public NotificationResponse postNotification(@RequestBody NotificationRequest request, @PathVariable String channelId) {
        Update update = request.getUpdate();
//        Subscription subscription = subscriptionRepository.findByChannelId(Long.valueOf(channelId));
        writeNotification(channelId, update);
        NotificationResponse response = new NotificationResponse();
        response.setStatus("accepted");
        return response;
    }

    private void writeNotification(@PathVariable String channelId, Update update) {
        TextChannel channel = findChannel(channelId);
        Message message = buildMessage(update, channel);
        message.write();
    }

    private Message buildMessage(Update update, TextChannel channel) {
        if (update.getSet() == null) {
            return new GenericMessage(channel, update.getDescription()); //TODO: Use SetUpdateMessage
        }
        return new GenericMessage(channel, update.getDescription());
    }

    @CheckForNull
    private TextChannel findChannel(String channelId) {
        return discordApi
                .getChannelById(Long.parseLong(channelId)).orElseThrow(IllegalArgumentException::new)
                .asServerTextChannel().orElse(null);
    }

}
