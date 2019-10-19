package dev.migwel.tournify.discordbot.message;

import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ParticipantsMessageWriter extends AbstractMessageWriter {

    private static final Logger log = LoggerFactory.getLogger(ParticipantsMessageWriter.class);
    private List<String> participants;

    public ParticipantsMessageWriter(TextChannel channel, List<String> participants) {
        super(channel);
        this.participants = participants;
    }

    protected String buildMessage() {
        String header = "List of participants: \n";
        return header + String.join(", ", participants);
    }
}
