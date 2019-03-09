package net.migwel.tournify.discordbot.response;

import net.migwel.tournify.discordbot.data.Player;

import java.util.List;

public class ParticipantsResponse {

    List<Player> participants;

    public List<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Player> participants) {
        this.participants = participants;
    }
}
