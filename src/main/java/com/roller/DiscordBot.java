package com.roller;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;
import java.util.Map;

import static com.roller.Main.logger;

public class DiscordBot extends ListenerAdapter {


    // Хранение команд и их обработчиков
    private final Map<String, CommandHandler> commands = new HashMap<>();

    // Метод для старта бота с необходимыми разрешениями
    public static void startBot(String botToken) {
        try {
            // Создает объект JDABuilder с токеном, подключается в Дискорд
            JDABuilder builder = JDABuilder.createDefault(botToken);

            // Включаем GatewayIntent для чтения содержимого сообщений, без этого бот не обрабатывает сообщения
            builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);

            builder.addEventListeners(new DiscordBot());  // Регистрирует экземпляр DiscordBot как слушателя событий
            builder.setActivity(Activity.playing("/5help для вызова справки."));  // Устанавливаем статус бота
            builder.setStatus(OnlineStatus.ONLINE);  // бот будет отображаться как "онлайн" для других пользователей

            // Стартуем бота
            builder.build();
            System.out.println("Бот успешно запущен!");
        } catch (InvalidTokenException e) {
            // Обрабатываем ошибку с токеном
            logger.error("Неверный токен бота. Проверьте значение переменной botToken.", e);
        } catch (Exception e) {
            // Обрабатываем другие ошибки
            logger.error("Произошла ошибка при запуске бота:", e);
        }
    }

    // Конструктор для добавления обработчиков команд
    public DiscordBot() {
        // Добавляем обработчики команд
        commands.put("/f", new FrollCommandHandler());
        commands.put("/5help", new HelpCommandHandler());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Проверяем, чтобы бот не отвечал на свои же сообщения
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw(); // Убираем лишние пробелы

        // Проверяем, начинается ли сообщение с /f (для команды с параметрами)
        if (message.startsWith("/f")) {
            CommandHandler commandHandler = commands.get("/f");
            if (commandHandler != null) {
                commandHandler.handleCommand(event, message);  // Передаем полное сообщение в обработчик
            }
        }
        // Проверяем, начинается ли сообщение с /5help (для команды помощи)
        else if (message.startsWith("/5help")) {
            CommandHandler helpCommandHandler = commands.get("/5help");
            if (helpCommandHandler != null) {
                helpCommandHandler.handleCommand(event, message);  // Передаем полное сообщение в обработчик
            }
        }
    }
}
