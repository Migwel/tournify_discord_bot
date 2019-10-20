package dev.migwel.tournify.discordbot.messagewriter;

import dev.migwel.tournify.communication.commons.Update;
import dev.migwel.tournify.communication.response.ParticipantsResponse;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class MessageWriterFactory {

    public MessageWriter getMessageWriter(TextChannel channel, Object messageContainer) {
        if(messageContainer instanceof Update) {
            return getUpdateMessageWriter(channel, (Update) messageContainer);
        }

        if(messageContainer instanceof ParticipantsResponse) {
            return new ParticipantsMessageWriter(channel, ((ParticipantsResponse)messageContainer).getParticipants());
        }

        throw new IllegalArgumentException("Wrong message container type: "+ messageContainer.getClass());
    }

    private MessageWriter getUpdateMessageWriter(TextChannel channel, Update update) {
        if (update.getSet() == null) {
            return new GenericMessageWriter(channel, update.getDescription()); //TODO: Use SetUpdateMessageWriter
        }
        return new GenericMessageWriter(channel, update.getDescription());
    }
}
