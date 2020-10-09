package dev.migwel.tournify.discordbot.service;

import dev.migwel.tournify.communication.request.SubscriptionRequest;
import dev.migwel.tournify.communication.response.SubscriptionResponse;
import dev.migwel.tournify.discordbot.ServerException;
import dev.migwel.tournify.discordbot.data.Subscription;
import dev.migwel.tournify.discordbot.properties.TournifyProperties;
import dev.migwel.tournify.discordbot.store.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.UUID;

@Service
public class SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    private RestTemplate restTemplate;
    private SubscriptionRepository subscriptionRepository;
    private TournifyProperties tournifyProperties;

    public SubscriptionService(RestTemplate restTemplate, SubscriptionRepository subscriptionRepository, TournifyProperties tournifyProperties) {
        this.restTemplate = restTemplate;
        this.subscriptionRepository = subscriptionRepository;
        this.tournifyProperties = tournifyProperties;
    }

    public void addSubscription(long channelId, String tournamentUrl, @Nullable String playerTag) throws ServerException {

        String callBackUrl = buildCallbackUrl(channelId);
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(tournamentUrl,
                callBackUrl,
                playerTag == null ? Collections.emptyList() : Collections.singletonList(playerTag));
        log.info("Subscribing to "+ tournamentUrl);
        SubscriptionResponse response;
        try {
            response = restTemplate.postForEntity(buildSubscribeUrl(), subscriptionRequest, SubscriptionResponse.class).getBody();
        }
        catch (RestClientException e) {
            log.warn("Something went wrong while calling the server", e);
            throw new ServerException("Could not subscribe to "+ tournamentUrl, e);
        }

        Subscription subscription = subscriptionRepository.findByTournamentUrlAndChannelId(tournamentUrl, channelId);
        if(subscription != null) {
            return;
        }

        subscription = new Subscription(UUID.fromString(response.getId()), tournamentUrl, channelId);
        subscriptionRepository.save(subscription);
    }

    private String buildSubscribeUrl() {
        return tournifyProperties.getTournifyUrl() + "/subscribe";
    }

    private String buildCallbackUrl(long channelId) {
        return tournifyProperties.getCallbackUrl() +"/notification/"+ channelId;
    }

    public void deleteSubscription(long channelId, String tournamentUrl) throws ServerException {
        Subscription subscription = subscriptionRepository.findByTournamentUrlAndChannelId(tournamentUrl, channelId);
        if(subscription == null) {
            log.info("Trying to delete a subscription that doesn't exist. channelId: "+ channelId +" - tournamentUrl: "+ tournamentUrl);
            return;
        }
        String id = subscription.getId().toString();
        try {
            restTemplate.delete(buildSubscribeUrl() + "/" + id);
        }
        catch (RestClientException e) {
            log.warn("Something went wrong while calling the server", e);
            throw new ServerException("Could not delete subscription to "+ tournamentUrl, e);
        }
        subscriptionRepository.delete(subscription);
    }
}
