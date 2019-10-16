package dev.migwel.tournify.discordbot.message;

import org.javacord.api.entity.channel.TextChannel;

public abstract class AbstractMessage implements Message{

    protected TextChannel channel;
    public static final int MAX_LENGTH = 2000;

    public AbstractMessage(TextChannel channel) {
        this.channel = channel;
    }
}
