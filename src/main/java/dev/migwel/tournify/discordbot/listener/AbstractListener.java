package dev.migwel.tournify.discordbot.listener;

import dev.migwel.tournify.discordbot.properties.DiscordProperties;
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
                "To subscribe: @mention subscribe [event URL] \n" +
                "Example: @TournifyBot subscribe https://smash.gg/tournament/genesis-3/events/melee-singles \n \n" +
                "To unsubscribe: @mention unsubscribe [event URL] \n" +
                "Example: @TournifyBot unsubscribe https://smash.gg/tournament/genesis-3/events/melee-singles \n \n" +
                "To get a list of participants: @mention participants [event URL] \n" +
                "Example: @TournifyBot participants https://smash.gg/tournament/genesis-3/events/melee-singles \n \n" +
                "If you have further questions or suggestions, join the Discord channel at https://discord.gg/D6GvMuR";
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(!botMentioned(event.getMessage().getMentionedUsers())) {
            return;
        }

        String message = event.getMessageContent();
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
                String playerTag = null;
                if (messageSplits.length > 3) {
                    playerTag = buildPlayerTag(messageSplits);
                }
                subscriptionListener.action(actionType, event.getChannel(), messageSplits[2], playerTag);
                break;
            case Participants:
                tournamentListener.action(event.getChannel(), messageSplits[2]);
        }

    }

    private String buildPlayerTag(String[] messageSplits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < messageSplits.length; i++) {
            sb.append(messageSplits[i]).append(" ");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private ActionType findActionType(String[] messageSplits) {
        if(messageSplits.length < 3) {
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
