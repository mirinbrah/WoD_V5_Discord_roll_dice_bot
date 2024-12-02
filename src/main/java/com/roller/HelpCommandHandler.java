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
                        Двойные десятки считаются как 4 успеха, сложность вычитает успехи. \s
                        Минимальный ввод выглядит как: /f dX, который вернет результат броска одного куба с X гранями.\s
                      """, false);
        embedBuilder.addField("",
                """
                        Все значения не должны быть отрицательными.\s
                        Прямое указание 0 в значении количества кубов или граней выведет ошибку.\s
                        Пропуск значения для количества кубов означает 1 куб.\s
                        Сложность является опциональной и при пустом значении считается равной 0.\s
                        Предельные значения кубов равны 100 для ограничения нарузки.\s
                      """, false);

        embedBuilder.setDescription("Команды:");
        embedBuilder.addField("/f [количество кубиков]d[грани] [сложность]",
                "Выполнить бросок кубиков. Например, '/f 5d10 2' - пять кубиков с 10 гранями, сложность 2.", false);
        embedBuilder.addField("/5help", "Покажет это сообщение со справкой.", false);
        embedBuilder.setColor(0x00FF00);

        // Отправляем сообщение
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
