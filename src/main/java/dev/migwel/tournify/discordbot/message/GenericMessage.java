package dev.migwel.tournify.discordbot.message;

import org.javacord.api.entity.channel.TextChannel;

public class GenericMessage extends AbstractMessage {

    private String text;

    public GenericMessage(TextChannel channel, String text) {
        super(channel);
        this.text = text;
    }

    @Override
    public void write(){
        channel.sendMessage(text);
    }
}
