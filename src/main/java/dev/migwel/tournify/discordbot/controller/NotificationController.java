package dev.migwel.tournify.discordbot.controller;

import dev.migwel.tournify.communication.commons.Update;
import dev.migwel.tournify.communication.request.NotificationRequest;
import dev.migwel.tournify.communication.response.NotificationResponse;
import dev.migwel.tournify.discordbot.messagewriter.MessageWriter;
import dev.migwel.tournify.discordbot.messagewriter.MessageWriterFactory;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.CheckForNull;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    private DiscordApi discordApi;

    private MessageWriterFactory messageWriterFactory;

    public NotificationController(DiscordApi discordApi, MessageWriterFactory messageWriterFactory) {
        this.discordApi = discordApi;
        this.messageWriterFactory = messageWriterFactory;
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.POST)
    public NotificationResponse postNotification(@RequestBody NotificationRequest request, @PathVariable String channelId) {
        Update update = request.getUpdate();
        writeNotification(channelId, update);
        NotificationResponse response = new NotificationResponse();
        response.setStatus("accepted");
        return response;
    }

    private void writeNotification(@PathVariable String channelId, Update update) {
        TextChannel channel;
        try {
            channel = findChannel(channelId);
        }
        catch (IllegalArgumentException e) {
            log.info("Could not find channel: "+ e.getMessage());
            return;
        }
        MessageWriter messageWriter = messageWriterFactory.getMessageWriter(channel, update);
        messageWriter.write();
    }

    @CheckForNull
    private TextChannel findChannel(String channelId) {
        return discordApi
                .getChannelById(Long.parseLong(channelId)).orElseThrow(() -> new IllegalArgumentException("Channel cannot be found "+ channelId))
                .asServerTextChannel().orElse(null);
    }

}
