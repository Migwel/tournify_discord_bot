package net.migwel.tournify.discordbot.controller;

import net.migwel.tournify.communication.commons.Update;
import net.migwel.tournify.communication.request.NotificationRequest;
import net.migwel.tournify.communication.response.NotificationResponse;
import net.migwel.tournify.discordbot.data.Subscription;
import net.migwel.tournify.discordbot.store.SubscriptionRepository;
import org.javacord.api.DiscordApi;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        Subscription subscription = subscriptionRepository.findByChannelId(Long.valueOf(channelId));
        discordApi.getChannelById(Long.parseLong(channelId)).ifPresent(
                ch -> ch.asServerTextChannel().ifPresent(serverTextChannel1 -> serverTextChannel1.sendMessage(update.getDescription())));
        NotificationResponse response = new NotificationResponse();
        response.setStatus("accepted");
        return response;
    }

}
