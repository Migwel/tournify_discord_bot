package dev.migwel.tournify.discordbot.message;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractMessageWriter implements MessageWriter {

    private static final Logger log = LoggerFactory.getLogger(ParticipantsMessageWriter.class);

    private TextChannel channel;
    private static final int MAX_LENGTH = 2000;

    public AbstractMessageWriter(TextChannel channel) {
        this.channel = channel;
    }

    @Override
    public void write() {
        String message = buildMessage();
        List<String> messagesToSend = cutMessage(message);
        for(String messageToSend : messagesToSend) {
            sendMessage(messageToSend);
        }
    }

    protected abstract String buildMessage();

    private List<String> cutMessage(String message) {
        List<String> messageParts = new ArrayList<>();
        for(int i = 0; i < message.length(); i += MAX_LENGTH) {
            messageParts.add(message.substring(i, Math.min(MAX_LENGTH, message.length())));
        }

        return messageParts;
    }

    private void sendMessage(String text) {
        CompletableFuture<Message> futureSuccessMessage = channel.sendMessage(text);
        try {
            futureSuccessMessage.get(1, TimeUnit.MINUTES);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            log.warn("Could not get success message", e);
        }
    }
}
