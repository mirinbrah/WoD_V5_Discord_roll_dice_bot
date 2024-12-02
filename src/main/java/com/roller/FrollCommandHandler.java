package com.roller;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrollCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(MessageReceivedEvent event, String message) {
        String result = diceRollCommand(message);
        // Отправляем результат в ответном сообщении, если он с ошибками, отправляем заглушку
        event.getChannel().sendMessage(result != null ? result : "Ошибка в формате ввода! \n " +
                " Используйте /f [количество кубиков]d[грани] [сложность]").queue();
    }

    private String diceRollCommand(String message) {
        String regex = "/f\\s*(\\d*)d(\\d+)\\s*(d(\\d+))?"; // Регулярное выражение
        Matcher matcher = Pattern.compile(regex).matcher(message); // Создаем matcher чтобы проверить сообщение

        if (matcher.matches()) {
            // Создаем объект DiceRoller со значениями из сообщения
            DiceRoller diceRoller = new DiceRoller(
                    parseInteger(matcher.group(1)), // Количество кубиков
                    parseInteger(matcher.group(2)), // Количество граней
                    parseInteger(matcher.group(4))  // Сложность
            );

            return diceRoller.getResults();
        }

        return null;
    }

    private Integer parseInteger(String value) {
        return (value == null || value.isEmpty()) ? null : Integer.parseInt(value);
    }
}



