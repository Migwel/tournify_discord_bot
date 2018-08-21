package net.migwel.tournify.discordbot.service;

import net.migwel.tournify.discordbot.request.SubscriptionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    private static final String localUrl = "http://localhost:8091"; //TODO: Put this in properties
    private static final String remoteUrl = "http://localhost:8090/subscribe"; //TODO: Put this in properties

    @Autowired
    private RestTemplate restTemplate;

    public void addSubscription(long channelId, String tournamentUrl) {

        String callBackUrl = localUrl +"/notification/"+ channelId;
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(tournamentUrl, callBackUrl);
        log.info("Subscribing to "+ tournamentUrl);
        restTemplate.put(remoteUrl, subscriptionRequest);
    }
}
