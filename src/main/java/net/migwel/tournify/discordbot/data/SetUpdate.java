package net.migwel.tournify.discordbot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class SetUpdate {

    private String description;

    public SetUpdate() {
    }

    public SetUpdate(String description) {
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
        final StringBuilder sb = new StringBuilder("SetUpdate{");
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
