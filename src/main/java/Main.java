import game.dialog.DialogEngine;
import game.engine.BattleEngine;
import game.io.SaveLoad;
import game.model.PlayerProfile;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("=== Консольный дуэльный прототип ===");

        // Попытка загрузки профиля, если не удалось — использование профиля по умолчанию
        PlayerProfile profile = SaveLoad.tryLoad("save.json")
                .orElseGet(() -> PlayerProfile.createDefault("Герой"));

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1) Диалог и выбор");
            System.out.println("2) Бой 1v1");
            System.out.println("3) Показать персонажа");
            System.out.println("4) Сохранить");
            System.out.println("5) Выход");
            System.out.print("> ");

            String cmd = in.nextLine().trim(); // Чтение команды из консоли

            switch (cmd) {
                case "1": // Диалог и выбор
                    DialogEngine.run(profile, in);
                    break;

                case "2": // Бой 1v1
                    BattleEngine.run(profile, in);
                    break;

                case "3": // Показать описание персонажа
                    System.out.println(profile.describe());
                    break;

                case "4": // Сохранение профиля
                    SaveLoad.save(profile, "save.json");
                    System.out.println("Сохранено в save.json");
                    break;

                case "5": // Выход
                    System.out.println("Пока!");
                    return;

                default: // Неизвестная команда
                    System.out.println("Неизвестная команда");
                    break;
            }
        }
    }
}