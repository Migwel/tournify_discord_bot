package dev.migwel.tournify.discordbot.message;

import dev.migwel.tournify.communication.commons.Set;
import org.javacord.api.entity.channel.TextChannel;

public class SetUpdateMessage extends AbstractMessage {

    private Set set;

    public SetUpdateMessage(TextChannel channel, Set set) {
        super(channel);
        this.set = set;
    }

    @Override
    public void write() {

    }
}
