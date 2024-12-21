import java.util.*;

public class HangmanGame {

    private static final int MAX_MISTAKES = 6; // Максимальное количество ошибок
    private static final List<String> words = Arrays.asList(
            "банк", "кредитка", "премиум", "наличные", "рефинансирование", "вклад", "счет", "подписка", "долями", "ипотека",
            "город", "доставка", "рестораны", "афиша", "инвестиции", "брокер", "акция", "процент", "кешбэк", "валюта",
            "фонд", "облигация", "фьючерсы", "золото", "стратегия", "портфель", "терминал", "маржа", "тариф", "роуминг",
            "секретарь", "страхование", "авиабилет", "бизнес", "эквайринг", "бухгалтерия", "депозит", "контрагент", "лизинг",
            "чаевые", "технология", "аналитика", "платформа", "корпоратив", "мессенджер", "корпорация", "селлер", "маркетплейс",
            "эксперт", "invoice", "java", "developer", "oracle", "maven", "mother", "love", "deploy", "application", "interface",
            "performance", "essential", "exception", "platform", "possible", "private", "backend"
    ); // Список слов
    private static final Set<Character> guessedLetters = new HashSet<>(); // Уже угаданные буквы

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Вы в игре Виселица. Угадайте слово или разъярённые крестьяне Вас повесят! ");
            String word = getRandomWord(); // Случайное слово
            String currentState = initializeState(word); // Инициализация состояния
            int mistakes = 0; // Количество ошибок

            // Вывод пустой виселицы в начале игры
            printHangman(mistakes);

            while (mistakes < MAX_MISTAKES && currentState.contains("-")) {
                System.out.println("Слово: " + currentState);
                System.out.println("Ошибок: " + mistakes + " из " + MAX_MISTAKES);
                System.out.println("Угаданные буквы: " + guessedLetters);

                System.out.print("Введите букву или слово целиком: ");
                String input = scanner.nextLine().toLowerCase();

                if (input.isEmpty()) {
                    System.out.println("Вы ничего не ввели. Попробуйте снова.");
                    continue;
                }

                if (input.length() == 1) { // Угадывание буквы
                    char letter = input.charAt(0);
                    if (!isValidLetter(letter)) {
                        System.out.println("Введите корректную букву (русские или английские).");
                        continue;
                    }
                    if (guessedLetters.contains(letter)) {
                        System.out.println("Вы уже вводили эту букву. Это вопрос жизни, соберитесь!");
                        continue;
                    }
                    guessedLetters.add(letter);

                    if (word.contains(String.valueOf(letter))) {
                        currentState = updateState(word, currentState, letter);
                    } else {
                        mistakes++;
                        System.out.println("Такой буквы нет в слове. Ошибок: " + mistakes);
                    }
                } else { // Угадывание слова целиком
                    if (input.equals(word)) {
                        currentState = word;
                    } else {
                        mistakes++;
                        System.out.println("Неверное слово. Ошибок: " + mistakes);
                    }
                }

                // Вывод виселицы после каждого хода
                printHangman(mistakes);
            }

            if (currentState.equals(word)) {
                printWin(); // Вывод сообщения о победе
                System.out.println("Поздравляем! Вы угадали слово: " + word);
            } else {
                printLose(); // Вывод сообщения о проигрыше
                System.out.println("Вы проиграли. Загаданное слово: " + word);
            }

            System.out.print("Хотите сыграть еще раз? (да/нет): ");
            String playAgain = scanner.nextLine().toLowerCase();
            if (!playAgain.equals("да")) {
                break;
            }
            guessedLetters.clear(); // Очищаем угаданные буквы для новой игры
        }

        scanner.close();
    }

    // Проверка, является ли слово корректным (на русском или английском)
    private static boolean isValidWord(String word) {
        for (char c : word.toCharArray()) {
            if (!isValidLetter(c)) {
                return false;
            }
        }
        return true;
    }

    // Проверка, является ли символ корректной буквой (русской или английской)
    private static boolean isValidLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'а' && c <= 'я');
    }

    // Получение случайного слова
    private static String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    // Инициализация состояния слова (прочерки)
    private static String initializeState(String word) {
        return "-".repeat(word.length());
    }

    // Обновление состояния слова
    private static String updateState(String word, String currentState, char letter) {
        StringBuilder newState = new StringBuilder(currentState);
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                newState.setCharAt(i, letter);
            }
        }
        return newState.toString();
    }

    // Вывод виселицы
    private static void printHangman(int mistakes) {
        switch (mistakes) {
            case 0:
                System.out.println(" __________");
                System.out.println(" |        |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("===========");
                break;
            case 1:
                System.out.println(" __________");
                System.out.println("   |      |  Вы видите как крестьяне водрузили эшафот с петлёй и пытаетесь бежать,");
                System.out.println(" (×﹏×)   |  но Вас поймали");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("===========");
                break;
            case 2:
                System.out.println(" __________");
                System.out.println("   |      |  Вы понимаете, что крестьяне не шутят, когда петля оказывается у Вас на шее");
                System.out.println(" (×﹏×)   |");
                System.out.println("  /|\\     |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("===========");
                break;
            case 3:
                System.out.println(" __________");
                System.out.println("   |      |  Вы вспоминаете как пили бабушкин компот на даче, и петля сдавливает шею сильнее");
                System.out.println(" (×﹏×)   |");
                System.out.println("  /|\\     |");
                System.out.println("   |      |");
                System.out.println("          |");
                System.out.println("          |");
                System.out.println("===========");
                break;
            case 4:
                System.out.println(" __________");
                System.out.println("   |      |  Правая нога немеет, и от копчика проходит волна к основанию затылка. Вы видите");
                System.out.println(" (×﹏×)   |  райский сад, в котором все ангелы улыбаются Вам и Вы слышите странный звук");
                System.out.println("  /|\\     |");
                System.out.println("   |      |");
                System.out.println("  /       |");
                System.out.println("          |");
                System.out.println("===========");
                break;
            case 5:
                System.out.println(" __________");
                System.out.println("   :      |  Это порвалась веревка на виселице! Надежда на жизнь мелькает перед взглядом, но");
                System.out.println(" (×﹏×)   |  Вы все еще висите, осознавая все ошибки судьбы");
                System.out.println("  /|\\     |");
                System.out.println("   |      |");
                System.out.println("  /       |");
                System.out.println("          |");
                System.out.println("===========");
                break;
            case 6:
                System.out.println(" __________");
                System.out.println("   |      |  Крестьянин замечает, что веревка рвется");
                System.out.println(" (×﹏×)   |  Он заматывает ее изолентой, и Вы задыхаетесь");
                System.out.println("  /|\\     |");
                System.out.println("   |      |");
                System.out.println("  / \\     |");
                System.out.println("          |");
                System.out.println("===========");
                break;
        }
    }

    // Вывод сообщения о проигрыше
    private static void printLose() {
        System.out.println("Вы видите свет в конце тоннеля, и идёте навстречу ангелам");
    }

    // Вывод сообщения о победе
    private static void printWin() {
        System.out.println("☆-☆-☆Победа! Вам удалось выбраться и спастись! ☆-☆-☆");
    }
}
