package dev.migwel.tournify.discordbot.messagewriter;

import dev.migwel.tournify.communication.commons.Update;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class MessageWriterFactory {

    public MessageWriter getMessageWriter(TextChannel channel, Update update) {
        if (update.getSet() == null) {
            return new GenericMessageWriter(channel, update.getDescription()); //TODO: Use SetUpdateMessageWriter
        }
        return new GenericMessageWriter(channel, update.getDescription());
    }
}
