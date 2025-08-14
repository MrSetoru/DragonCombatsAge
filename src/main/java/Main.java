import game.io.SaveLoad;
import game.model.PlayerProfile;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("=== Консольный дуэльный прототип ===");
        PlayerProfile profile = SaveLoad.tryLoad("save.json")
                .orElseGet(() -> PlayerProfile.createDefault("Герой"));
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1) Диалог и выбор");
            System.out.println("2) Бой 1v1");
            System.out.println("3) Показать персонажа");
            System.out.println("4) Сохранить");
            System.out.println("5) Выход");
            System.out.print("&gt; ");
            String cmd = in.nextLine().trim();
            switch (cmd) {
                case "1" - &gt
                    ;
                    DialogEngine.run(profile, in);
                case "2" - & gt
                    ;
                    BattleEngine.run(profile, in);
                case "3" - & gt
                    ;
                    System.out.println(profile.describe());
                case "4" - & gt
                    ;
                {
                    SaveLoad.save(profile, "save.json");
                    System.out.println("Сохранено в save.json");
                }
                case "5" - & gt
                    ;
                {
                    System.out.println("Пока!");
                    return;
                }
                default
                    - & gt; System.out.println("Неизвестная команда");
            }
        }
    }
}
