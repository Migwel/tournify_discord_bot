package dev.migwel.tournify.discordbot.message;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ParticipantsMessage extends AbstractMessage {

    private static final Logger log = LoggerFactory.getLogger(ParticipantsMessage.class);
    private List<String> participants;

    public ParticipantsMessage(TextChannel channel, List<String> participants) {
        super(channel);
        this.participants = participants;
    }

    @Override
    public void write() {
        sendHeader();
        sendParticipantList();
    }

    private void sendParticipantList() {
        StringBuilder participantsBuilder = new StringBuilder();
        for(int i = 0; i < participants.size(); i++) {
            String participant = participants.get(i);
            participantsBuilder.append(participant).append(", ");
            String nextParticipant = getParticipant(i+1);
            if(messageReadyToSend(participantsBuilder.toString(), nextParticipant)) {
                String participantsStr = removeExtraComma(participantsBuilder);
                sendMessage(participantsStr);
                participantsBuilder = new StringBuilder();
            }
        }
    }

    private String removeExtraComma(StringBuilder participantsBuilder) {
        return participantsBuilder.substring(0, participantsBuilder.length() - 1);
    }

    private boolean messageReadyToSend(String participantsStr, String nextParticipant) {
        if(nextParticipant == null) {
            return true;
        }

        return participantsStr.length() + nextParticipant.length() > MAX_LENGTH;
    }

    private String getParticipant(int i) {
        if(i >= participants.size()) {
            return null;
        }
        return participants.get(i);
    }

    private void sendHeader() {
        String header = "List of participants: \n";
        sendMessage(header);
    }

    private void sendMessage(String text) {
        CompletableFuture<Message> futureSuccessMessage = channel.sendMessage( text);
        try {
            futureSuccessMessage.get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            log.warn("Could not get success message", e);
        }
    }
}
