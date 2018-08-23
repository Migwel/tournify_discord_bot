package net.migwel.tournify.discordbot.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"tournamentUrl", "channelId"})}
)
public class Subscription {

    @Id
    private UUID id;

    private String tournamentUrl;

    private long channelId;

    public Subscription() {
    }

    public Subscription(UUID id, String tournamentUrl, long channelId) {
        this.id = id;
        this.tournamentUrl = tournamentUrl;
        this.channelId = channelId;
    }

    public UUID getId() {
        return id;
    }

    public String getTournamentUrl() {
        return tournamentUrl;
    }

    public void setTournamentUrl(String tournamentUrl) {
        this.tournamentUrl = tournamentUrl;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subscription{");
        sb.append("id=").append(id);
        sb.append(", tournamentUrl=").append(tournamentUrl);
        sb.append(", channelId=").append(channelId);
        sb.append('}');
        return sb.toString();
    }
}
