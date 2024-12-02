package com.roller;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommandHandler implements CommandHandler {

    @Override
    public void handleCommand(MessageReceivedEvent event, String message) {

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Справка по Quinque");

        embedBuilder.setDescription("Это простой бот для WoD V5");

        embedBuilder.addField("",
                """
                        Бот позволяет кидать кубы вида [количество кубиков]d[грани] и выводит результат.\s
                        Если бросаются десятигранники, он подсчитывает их по системе WoD V5.\s
                        Двойные десятки считаются как 4 успеха, сложность вычитает успехи.\s
                       """, false);
        embedBuilder.addField("",
                """
                         Количество кубов и сложность опциональны, минимальный ввод выглядит как:\s
                         '/f dX' \s
                         который вернет результат броска одного куба с X гранями.\s
                         Все значения должны быть положительными или равны нулю, \s
                         кроме значения количества граней, которое должно быть больше нуля.\s
                      \s""", false);

        embedBuilder.setDescription("Команды:");
        embedBuilder.addField("/f [количество кубиков]d[грани] [сложность]",
                "Выполнить бросок кубиков. Например, '/f 5d10 2' - пять кубиков с 10 гранями, сложность 2.", false);
        embedBuilder.addField("/5help", "Покажет это сообщение со справкой.", false);
        embedBuilder.setColor(0x00FF00);

        // Отправляем сообщение
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
