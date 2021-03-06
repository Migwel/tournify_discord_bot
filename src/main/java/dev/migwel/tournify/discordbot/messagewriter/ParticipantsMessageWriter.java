package dev.migwel.tournify.discordbot.messagewriter;

import dev.migwel.tournify.communication.commons.Player;
import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

public class ParticipantsMessageWriter extends AbstractMessageWriter {

    private static final Logger log = LoggerFactory.getLogger(ParticipantsMessageWriter.class);
    private Collection<Player> participants;

    public ParticipantsMessageWriter(TextChannel channel, Collection<Player> participants) {
        super(channel);
        this.participants = participants;
    }

    protected String buildMessage() {
        String header = "List of participants: \n";
        return header + participants.stream().map(Player::getDisplayUsername).collect(Collectors.joining(", ")) ;
    }
}
