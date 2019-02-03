package net.migwel.tournify.discordbot.listener;

import net.migwel.tournify.discordbot.DiscordProperties;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AbstractListener implements MessageCreateListener {

    private static final Logger log = LoggerFactory.getLogger(AbstractListener.class);

    public enum ActionType {
        Subscribe,
        Unsubscribe,
        Participants,
        ;
    }

    private DiscordProperties discordProperties;
    private SubscriptionListener subscriptionListener;
    private TournamentListener tournamentListener;

    public AbstractListener(DiscordProperties discordProperties,
                            SubscriptionListener subscriptionListener,
                            TournamentListener tournamentListener) {
        this.discordProperties = discordProperties;
        this.subscriptionListener = subscriptionListener;
        this.tournamentListener = tournamentListener;
    }

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
        ActionType actionType = findActionType(messageSplits);
        if(actionType == null) {
            event.getChannel().sendMessage(usage());
            return;
        }

        switch (actionType) {
            case Subscribe:
            case Unsubscribe:
                subscriptionListener.action(actionType, event.getChannel().getId(), messageSplits[2]);
                break;
            case Participants:
                tournamentListener.action(event.getChannel(), messageSplits[2]);
        }

    }

    private ActionType findActionType(String[] messageSplits) {
        if(messageSplits.length != 3) {
            return null;
        }

        if(!validUrl(messageSplits[2])) {
            return null;
        }

        switch (messageSplits[1].toLowerCase()) {
            case "subscribe":
                return ActionType.Subscribe;
            case "unsubscribe":
                return ActionType.Unsubscribe;
            case "participants":
                return ActionType.Participants;
            default:
                return null;
        }
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
