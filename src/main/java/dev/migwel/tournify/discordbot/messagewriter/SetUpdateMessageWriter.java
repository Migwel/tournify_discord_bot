package dev.migwel.tournify.discordbot.messagewriter;

import dev.migwel.tournify.communication.commons.Player;
import dev.migwel.tournify.communication.commons.Set;
import org.javacord.api.entity.channel.TextChannel;

import java.util.Collection;
import java.util.stream.Collectors;

public class SetUpdateMessageWriter extends AbstractMessageWriter {

    private Set set;

    public SetUpdateMessageWriter(TextChannel channel, Set set) {
        super(channel);
        this.set = set;
    }

    @Override
    protected String buildMessage() {
        return "Tournament [" +
                set.getTournamentName() +
                "] - Phase [" +
                set.getPhaseName() +
                "] - Set [" +
                set.getRound() +
                "] - " +
                set.getPlayers().stream().map(Player::getDisplayUsername).collect(Collectors.joining(" vs ")) +
                " - " + buildWinnerMessage(set.getWinners());
    }

    private String buildWinnerMessage(Collection<Player> winners) {
        if (winners == null || winners.isEmpty()) {
            return "It's a draw";
        }

        return "Winner is " +
                winners.stream().map(Player::getDisplayUsername).collect(Collectors.joining(" vs "));
    }
}
