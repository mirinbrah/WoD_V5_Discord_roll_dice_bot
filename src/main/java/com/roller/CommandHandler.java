package com.roller;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandHandler {
    void handleCommand(MessageReceivedEvent event, String message);
}