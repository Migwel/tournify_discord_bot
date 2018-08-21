package net.migwel.tournify.discordbot.listener;

import net.migwel.tournify.discordbot.DiscordProperties;
import net.migwel.tournify.discordbot.service.SubscriptionService;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionListener implements MessageCreateListener {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionListener.class);

    @Autowired
    private DiscordProperties discordProperties;

    @Autowired
    private SubscriptionService subscriptionService;

    private String usage() {
        return "Usage: \n" +
                "@mention subscribe [event URL] \n" +
                "Example: @TournifyBot subscribe https://smash.gg/tournament/genesis-3/events/melee-singles \n";
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(!botMentioned(event.getMessage().getMentionedUsers())) {
            return;
        }

        String message = event.getMessage().getContent();
        if(message == null || message.isEmpty()) {
            event.getChannel().sendMessage(usage());
            return;
        }

        String[] messageSplits = message.split("\\s");
        if(!validParameters(messageSplits)) {
            event.getChannel().sendMessage(usage());
        }

        subscriptionService.addSubscription(event.getChannel().getId(), messageSplits[2]);
    }

    private boolean validParameters(String[] messageSplits) {
        if(messageSplits.length != 3) {
            return false;
        }

        if(!"subscribe".equals(messageSplits[1].toLowerCase())) {
            return false;
        }

        return validUrl(messageSplits[2]);
    }

    private boolean validUrl(String messageSplit) {
        return true;
    }

    private boolean botMentioned(List<User> mentionedUsers) {
        Long botId = discordProperties.getBotId();
        if(botId == null) {
            log.warn("Bot ID is not defined");
            return false;
        }

        for(User user : mentionedUsers) {
            if(user.getId() == botId) {
                return true;
            }
        }

        return false;
    }
}
