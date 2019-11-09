package dev.migwel.tournify.discordbot.service;

import dev.migwel.tournify.communication.request.TournamentRequest;
import dev.migwel.tournify.communication.response.ParticipantsResponse;
import dev.migwel.tournify.discordbot.properties.TournifyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;

@Service
public class TournamentService {

    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);

    private RestTemplate restTemplate;
    private TournifyProperties tournifyProperties;

    public TournamentService(RestTemplate restTemplate, TournifyProperties tournifyProperties) {
        this.restTemplate = restTemplate;
        this.tournifyProperties = tournifyProperties;
    }

    @Nonnull
    public ParticipantsResponse getParticipants(String tournamentUrl) {
        TournamentRequest participantsRequest = new TournamentRequest(tournamentUrl);
        log.info("Getting participants for "+ tournamentUrl);
        return restTemplate.postForEntity(buildParticipantsUrl(), participantsRequest, ParticipantsResponse.class).getBody();
    }

    private String buildParticipantsUrl() {
        return tournifyProperties.getTournifyUrl() + "/participants";
    }
}
