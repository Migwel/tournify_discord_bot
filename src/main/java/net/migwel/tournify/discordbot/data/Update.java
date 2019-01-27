package net.migwel.tournify.discordbot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Update {

    private String description;

    public Update() {
    }

    public Update(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Update{");
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
