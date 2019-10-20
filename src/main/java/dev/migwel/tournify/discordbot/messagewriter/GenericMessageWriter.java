package dev.migwel.tournify.discordbot.messagewriter;

import org.javacord.api.entity.channel.TextChannel;

public class GenericMessageWriter extends AbstractMessageWriter {

    private String message;

    public GenericMessageWriter(TextChannel channel, String message) {
        super(channel);
        this.message = message;
    }

    @Override
    public String buildMessage(){
        return message;
    }
}
