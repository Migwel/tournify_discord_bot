package dev.migwel.tournify.discordbot.messagewriter;

import dev.migwel.tournify.communication.commons.SetUpdate;
import dev.migwel.tournify.communication.commons.Update;
import dev.migwel.tournify.communication.response.ParticipantsResponse;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class MessageWriterFactory {

    public MessageWriter getMessageWriter(TextChannel channel, Object messageContainer) {
        if(messageContainer instanceof Update update) {
            return getUpdateMessageWriter(channel, update);
        }

        if(messageContainer instanceof ParticipantsResponse participantsResponse) {
            return new ParticipantsMessageWriter(channel, participantsResponse.getParticipants());
        }

        if(messageContainer instanceof String genericMessage) {
            return new GenericMessageWriter(channel, genericMessage);
        }

        throw new IllegalArgumentException("Wrong message container type: "+ messageContainer.getClass());
    }

    private MessageWriter getUpdateMessageWriter(TextChannel channel, Update update) {
        if (update instanceof SetUpdate setUpdate) {
            return new SetUpdateMessageWriter(channel, setUpdate.getSet());
        }
        return new GenericMessageWriter(channel, update.getDescription());
    }
}
