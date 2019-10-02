package net.migwel.tournify.discordbot.listener;

import net.migwel.tournify.discordbot.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionListener {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionListener.class);

    private SubscriptionService subscriptionService;

    public SubscriptionListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public void action(AbstractListener.ActionType actionType, long channelId, String url, String playerTag) {
        switch (actionType) {
            case Subscribe:
                subscriptionService.addSubscription(channelId, url, playerTag);
                break;
            case Unsubscribe:
                subscriptionService.deleteSubscription(channelId, url);
                break;
        }
    }
}
