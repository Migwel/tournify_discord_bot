package net.migwel.tournify.discordbot.store;

import net.migwel.tournify.discordbot.data.Subscription;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Subscription findByTournamentUrlAndChannelIdAndPlayerTag(String tournamentUrl, long channelId, String playerTag);
    List<Subscription> findByChannelId(long channelId);
}
