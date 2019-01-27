package net.migwel.tournify.discordbot.controller;

import net.migwel.tournify.discordbot.data.Update;
import net.migwel.tournify.discordbot.data.Updates;
import net.migwel.tournify.discordbot.request.NotificationRequest;
import net.migwel.tournify.discordbot.response.NotificationResponse;
import org.javacord.api.DiscordApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private DiscordApi discordApi;

    @RequestMapping(value = "/{channelId}", method = RequestMethod.POST)
    public NotificationResponse postNotification(@RequestBody NotificationRequest request, @PathVariable String channelId) {
        Updates updates = request.getUpdates();
        for (Update update : updates.getUpdateList()) {
            discordApi.getChannelById(Long.parseLong(channelId)).ifPresent(
                    ch -> ch.asServerTextChannel().ifPresent(serverTextChannel1 -> serverTextChannel1.sendMessage(update.getDescription())));
        }
        NotificationResponse response = new NotificationResponse();
        response.setStatus("accepted");
        return response;
    }
}
