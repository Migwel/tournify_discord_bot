package net.migwel.tournify.discordbot.controller;

import net.migwel.tournify.communication.commons.Player;
import net.migwel.tournify.communication.commons.Update;
import net.migwel.tournify.communication.commons.Updates;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        Updates updates = request.getUpdates();
        List<Subscription> subscriptionList = subscriptionRepository.findByChannelId(Long.valueOf(channelId));
        for (Update update : updates.getUpdateList()) {
            if(!relevantUpdate(update, subscriptionList)) {
                continue;
            }
            discordApi.getChannelById(Long.parseLong(channelId)).ifPresent(
                    ch -> ch.asServerTextChannel().ifPresent(serverTextChannel1 -> serverTextChannel1.sendMessage(update.getDescription())));
        }
        NotificationResponse response = new NotificationResponse();
        response.setStatus("accepted");
        return response;
    }

    private boolean relevantUpdate(Update update, List<Subscription> subscriptionList) {
        if(subscriptionList == null || subscriptionList.isEmpty()) {
            return false;
        }

        if(update.getSet() == null) {
            return true;
        }
        Collection<Player> setPlayers = update.getSet().getPlayers();

        if(setPlayers.isEmpty()) {
            return true;
        }

        List<String> followedPlayers = new ArrayList<>();

        for(Subscription subscription : subscriptionList) {
            if(subscription.getPlayerTag() != null && !subscription.getPlayerTag().isEmpty()) {
                followedPlayers.add(subscription.getPlayerTag());
            }
        }

        if(followedPlayers.isEmpty()) {
            return true;
        }

        for(Player setPlayer : setPlayers) {
            if (followedPlayers.contains(setPlayer.getDisplayUsername())) {
                return true;
            }
        }

        return false;
    }
}
