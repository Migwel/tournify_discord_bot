package dev.migwel.tournify.discordbot.store;

import dev.migwel.tournify.discordbot.data.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Subscription findByTournamentUrlAndChannelId(String tournamentUrl, long channelId);
    Subscription findByChannelId(long channelId);
}
