package com.roller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    //библиотека JDA ругается если не подключить логирование по-серьёзному
    static final Logger logger = LoggerFactory.getLogger(Main.class);

    //стандартная точка входа для джава
    public static void main(String[] args) {
        //уникальный токен бота
        String botToken = "токен вашего бота в кавычках";

        DiscordBot.startBot(botToken); //запускаем бота, логика "как" в отдельном классе DiscordBot
    }
}

/*
Я использовал стандартную практику разделения логики на блоки

Main - это основной файл через который все запускается

DiscordBot - в этом файле все, что касается логики работы дискорд бота и обработки команд

DiceRoller - хранит переменные и логику для бросков кубов
*/
