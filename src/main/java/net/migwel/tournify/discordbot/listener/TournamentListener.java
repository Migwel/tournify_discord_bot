package net.migwel.tournify.discordbot.listener;

import net.migwel.tournify.discordbot.service.TournamentService;
import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class TournamentListener {

    private static final Logger log = LoggerFactory.getLogger(TournamentListener.class);

    private TournamentService tournamentService;

    public TournamentListener(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    private String usage() {
        return "Usage: \n" +
                "@mention subscribe [event URL] \n" +
                "Example: @TournifyBot participants https://smash.gg/tournament/genesis-3/events/melee-singles \n";
    }

    public void action(TextChannel channel, String url) {
        List<String> participants = tournamentService.getParticipants(url);
        writeParticipants(channel, participants);
    }

    private void writeParticipants(TextChannel channel, @Nonnull List<String> participants) {
        channel.sendMessage("List of participants: \n" + String.join(",  ", participants));
    }
}