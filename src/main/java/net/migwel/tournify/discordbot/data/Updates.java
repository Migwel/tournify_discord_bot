package net.migwel.tournify.discordbot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class Updates {

    private List<Update> updateList;
    private boolean tournamentDone;

    public Updates() {
    }

    public List<Update> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<Update> updateList) {
        this.updateList = updateList;
    }

    public boolean isTournamentDone() {
        return tournamentDone;
    }

    public void setTournamentDone(boolean tournamentDone) {
        this.tournamentDone = tournamentDone;
    }
}