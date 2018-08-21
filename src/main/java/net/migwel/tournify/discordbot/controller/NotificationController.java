package net.migwel.tournify.discordbot.controller;

import net.migwel.tournify.discordbot.data.SetUpdate;
import net.migwel.tournify.discordbot.request.NotificationRequest;
import net.migwel.tournify.discordbot.response.NotificationResponse;
import org.javacord.api.DiscordApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private DiscordApi discordApi;

    @RequestMapping(method = RequestMethod.POST)
    public NotificationResponse postNotification(@RequestBody NotificationRequest request) {
        List<SetUpdate> setUpdates = request.getSetUpdates();
        String content = setUpdates.stream().map(SetUpdate::getDescription).collect(Collectors.joining("\n"));
        discordApi.getChannelsByName("test-bot").forEach(
                ch -> ch.asServerTextChannel().ifPresent(serverTextChannel1 -> serverTextChannel1.sendMessage(content)));
        NotificationResponse response = new NotificationResponse();
        response.setStatus("accepted");
        return response;
    }
}
