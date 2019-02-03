package net.migwel.tournify.discordbot.service;

import net.migwel.tournify.discordbot.data.Participant;
import net.migwel.tournify.discordbot.request.ParticipantsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

@Service
public class TournamentService {

    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);

    private static final String remoteUrl = "http://localhost:8090/tournament"; //TODO: Put this in properties
    private static final String participantsUrl = remoteUrl + "/participants";


    @Autowired
    private RestTemplate restTemplate;

    @Nonnull
    public List<Participant> getParticipants(String tournamentUrl) {
        ParticipantsRequest participantsRequest = new ParticipantsRequest(tournamentUrl);
        log.info("Getting participants for "+ tournamentUrl);
        List<Participant> participants = (List<Participant>)restTemplate.postForEntity(participantsUrl, participantsRequest, List.class).getBody();
        return participants == null ? Collections.emptyList() : participants;
    }
}
