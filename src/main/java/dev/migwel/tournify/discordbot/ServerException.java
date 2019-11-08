package dev.migwel.tournify.discordbot;

public class ServerException extends Exception {
    public ServerException() {}
    public ServerException(String cause) {super(cause);}
    public ServerException(Throwable t) {super(t);}
    public ServerException(String cause, Throwable t) {super(cause, t);}
}
