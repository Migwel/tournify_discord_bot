package dev.migwel.tournify.discordbot.listener;

import dev.migwel.tournify.communication.response.ParticipantsResponse;
import dev.migwel.tournify.discordbot.messagewriter.MessageWriter;
import dev.migwel.tournify.discordbot.messagewriter.MessageWriterFactory;
import dev.migwel.tournify.discordbot.service.TournamentService;
import org.javacord.api.entity.channel.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class TournamentListener {

    private TournamentService tournamentService;
    private MessageWriterFactory messageWriterFactory;

    public TournamentListener(TournamentService tournamentService, MessageWriterFactory messageWriterFactory) {
        this.tournamentService = tournamentService;
        this.messageWriterFactory = messageWriterFactory;
    }

    private String usage() {
        return "Usage: \n" +
                "@mention participants [event URL] \n" +
                "Example: @TournifyBot participants https://smash.gg/tournament/genesis-3/events/melee-singles \n";
    }

    public void action(TextChannel channel, String url) {
        ParticipantsResponse participantsResponse = tournamentService.getParticipants(url);
        writeParticipants(channel, participantsResponse);
    }

    private void writeParticipants(TextChannel channel, ParticipantsResponse participantsResponse) {
        MessageWriter messageWriter = messageWriterFactory.getMessageWriter(channel, participantsResponse);
        messageWriter.write();
    }
}
