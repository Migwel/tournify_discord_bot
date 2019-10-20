package dev.migwel.tournify.discordbot.service;

import dev.migwel.tournify.communication.request.TournamentRequest;
import dev.migwel.tournify.communication.response.ParticipantsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;

@Service
public class TournamentService {

    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);

    private static final String remoteUrl = "http://migwel.dev:8090/tournament"; //TODO: Put this in properties
    private static final String participantsUrl = remoteUrl + "/participants";

    private RestTemplate restTemplate;

    public TournamentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Nonnull
    public ParticipantsResponse getParticipants(String tournamentUrl) {
        TournamentRequest participantsRequest = new TournamentRequest(tournamentUrl);
        log.info("Getting participants for "+ tournamentUrl);
        ParticipantsResponse participantsResponse = restTemplate.postForEntity(participantsUrl, participantsRequest, ParticipantsResponse.class).getBody();
        return participantsResponse;
    }
}
