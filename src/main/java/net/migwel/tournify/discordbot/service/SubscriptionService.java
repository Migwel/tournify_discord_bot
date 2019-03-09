package net.migwel.tournify.discordbot.service;

import net.migwel.tournify.discordbot.data.Subscription;
import net.migwel.tournify.discordbot.request.SubscriptionRequest;
import net.migwel.tournify.discordbot.response.SubscriptionResponse;
import net.migwel.tournify.discordbot.store.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    private static final String localUrl = "https://polar-river-52151.herokuapp.com"; //TODO: Put this in properties
    private static final String remoteUrl = "https://vast-cove-59499.herokuapp.com/subscribe"; //TODO: Put this in properties

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public void addSubscription(long channelId, String tournamentUrl) {

        String callBackUrl = localUrl +"/notification/"+ channelId;
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(tournamentUrl, callBackUrl);
        log.info("Subscribing to "+ tournamentUrl);
        SubscriptionResponse response = restTemplate.postForEntity(remoteUrl, subscriptionRequest, SubscriptionResponse.class).getBody();

        Subscription subscription = new Subscription(UUID.fromString(response.getId()), tournamentUrl, channelId);
        subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(long channelId, String tournamentUrl) {
        Subscription subscription = subscriptionRepository.findByTournamentUrlAndChannelId(tournamentUrl, channelId);
        if(subscription == null) {
            return;
        }
        String id = subscription.getId().toString();
        restTemplate.delete(remoteUrl + "/" + id);
        subscriptionRepository.delete(subscription);
    }
}
