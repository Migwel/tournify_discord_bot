package net.migwel.tournify.discordbot.response;

import net.migwel.tournify.discordbot.data.Participant;

import java.util.List;

public class ParticipantsResponse {

    List<Participant> participants;

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
