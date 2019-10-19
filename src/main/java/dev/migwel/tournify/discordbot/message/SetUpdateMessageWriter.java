package dev.migwel.tournify.discordbot.message;

import dev.migwel.tournify.communication.commons.Set;
import org.javacord.api.entity.channel.TextChannel;

public class SetUpdateMessageWriter extends AbstractMessageWriter {

    private Set set;

    public SetUpdateMessageWriter(TextChannel channel, Set set) {
        super(channel);
        this.set = set;
    }

    @Override
    protected String buildMessage() {
        return null;
    }
}
