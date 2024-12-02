package com.roller;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiceRoller {
    private static final int SUCCESS_DIE = 6; // Значение на кубе, которое делает его успехом
    private static final int MIN_NUMBER_OF_DICES = 1; // Минимальное количество кубов
    private static final int MIN_DIFFICULTY = 0; // Минимальная сложность
    private static final int MAX_VALUE = 100; // Максимальное значение для кубов, граней и сложности

    private final Integer numberOfDices;
    final Integer numberOfSides;
    private final Integer difficulty;
    private int successes;

    // Конструктор класса с значениями (количество кубов, количество граней, сложность)
    public DiceRoller(Integer numberOfDices, Integer numberOfSides, Integer difficulty) {
        // Проверяем, если количество кубов 0, то сразу возвращаем его как 0
        if (numberOfDices != null && numberOfDices == 0) {
            this.numberOfDices = 0;
        } else {
            this.numberOfDices = (numberOfDices != null && numberOfDices >= MIN_NUMBER_OF_DICES) ? numberOfDices : MIN_NUMBER_OF_DICES;
        }

        this.numberOfSides = (numberOfSides != null && numberOfSides > 0) ? numberOfSides : null;
        this.difficulty = (difficulty != null && difficulty >= MIN_DIFFICULTY) ? difficulty : MIN_DIFFICULTY;
    }

    // Метод для генерации результатов бросков
    private List<Integer> rollDice(int numberOfDices, int numberOfSides) {
        Random random = new Random();
        return IntStream.range(0, numberOfDices)
                .mapToObj(i -> random.nextInt(numberOfSides) + 1)
                .collect(Collectors.toList());
    }

    // Метод для подсчета успехов
    private int countSuccesses(List<Integer> rollResults, StringBuilder result) {
        Map<String, Long> stats = rollResults.stream()
                .collect(Collectors.groupingBy(
                        roll -> roll == 10 ? "tens" : (roll >= SUCCESS_DIE ? "successes" : "others"),
                        Collectors.counting()
                ));

        int tens = stats.getOrDefault("tens", 0L).intValue();
        successes = stats.getOrDefault("successes", 0L).intValue();

        int doubleTens = tens / 2;
        successes += doubleTens * 4 + (tens % 2);

        result.append(Stream.of(
                        doubleTens > 0 ? "Двойных десяток: " + doubleTens + "\n" : "",
                        tens >= 2 ? "Бросок был критическим!\n" : "",
                        successes == 0 ? "Бросок был провальным!\n" : "")
                .filter(msg -> !msg.isEmpty())
                .collect(Collectors.joining())
        );

        return successes;
    }

    // Метод для получения результата в виде строки
    public String getResults() {
        // Проверяем ошибки перед выполнением бросков
        String validationError = validateInputs();
        if (validationError != null) {
            return validationError;
        }

        // Выполняем бросок
        List<Integer> rollResults = rollDice(numberOfDices, numberOfSides);

        // Если количество граней не равно 10, просто возвращаем результаты бросков
        if (numberOfSides != 10) {
            return "Результаты броска: " + rollResults;
        }

        // Формируем строку результатов бросков
        String formattedResults = rollResults.stream()
                .map(this::formatRoll)
                .collect(Collectors.joining(" "));
        StringBuilder result = new StringBuilder("Результаты броска: ").append(formattedResults).append("\n");

        // Подсчет успехов и итоговый результат
        successes = countSuccesses(rollResults, result);
        int finalResult = calculateFinalResult();

        // Формируем сообщения для результата
        List<String> messages = Stream.of(
                successes > 0 && difficulty == 0 ? "Количество успехов: " + successes + "\n" : "",
                difficulty > 0 ? "Сложность: " + difficulty + "\n" : "",
                finalResult >= 0 && difficulty > 0 ? "Количество успехов выше сложности: " + finalResult + "\n" : "",
                finalResult >= 0 && difficulty > 0 ? "Вы преодолели сложность!\n" : "",
                finalResult < 0 ? "Это было слишком сложно, у вас не получилось.\n" : "",
                finalResult < 0 ? "Количество успехов которых вам не хватило: " + Math.abs(finalResult) : ""
        ).filter(msg -> !msg.isEmpty()).collect(Collectors.toList());

        // Собираем финальный результат
        result.append(String.join("", messages));
        return result.toString();
    }

    // Метод для расчета итогового результата
    private int calculateFinalResult() {
        return successes - difficulty;
    }

    // Метод для проверки значений
    private String validateInputs() {
        if (numberOfDices == 0) {
            return "Ошибка: количество кубов не может быть равно 0.";
        }
        if (numberOfDices > MAX_VALUE) {
            return "Ошибка: количество кубов не может быть больше 100.";
        }
        if (numberOfSides == null) {
            return "Ошибка: количество граней не может быть равно 0.";
        }
        if (numberOfSides > MAX_VALUE) {
            return "Ошибка: количество граней не может быть больше 100.";
        }
        if (difficulty > MAX_VALUE) {
            return "Ошибка: сложность не может быть больше 100.";
        }
        return null; //Ошибок нет, идем дальше
    }

    // Метод для изменения форматирования бросков
    private String formatRoll(int roll) {
        if (roll == 10) {
            return "**" + roll + "**"; // Жирный текст для десяток
        } else if (roll >= SUCCESS_DIE) {
            return "__" + roll + "__"; // Подчеркнутый для успехов
        } else {
            return String.valueOf(roll); // Серый текст для остальных
        }
    }
}
