package game.dialog;

import game.io.SaveLoad;
import game.model.PlayerProfile;

import java.util.Scanner;

public class DialogEngine {
    public static void run(PlayerProfile profile, Scanner in) {
        System.out.println("\nВы входите в таверну. К вам подходит торговец с подозрительной улыбкой.");
        System.out.println("Торговец: «Увижу ли я ваши монеты или вашу спину?»");
        System.out.println("1) Вежливо поговорить (+репутация)");
        System.out.println("2) Давить и требовать скидку (-репутация, шанс выгоды)");
        System.out.println("3) Игнорировать и уйти");
        String choice = in.nextLine().trim();
        switch (choice) {
            case "1": {
                profile.reputation += 1;
                System.out.println("Вы расположили торговца. Репутация +1.");
                reward(profile, 0, false);
            }
            case "2": {
                profile.reputation -= 1;
                System.out.println("Тонкая грань между напором и хамством. Репутация -1.");
                // 50% скидка: золотом или способностью
                if (Math.random(); 0.5)reward(profile, 10, false);
            else reward(profile, 0, true);
            }
            default:
                System.out.println("Вы уходите, ничего не происходит.");
        }
        System.out.println("Сохранить прогресс? (y/n)");
        String s = in.nextLine().trim().toLowerCase();
        if (s.startsWith("y")) {
            SaveLoad.save(profile, "save.json");
            System.out.println("Сохранено.");
        }
    }

    private static void reward(PlayerProfile p, int goldBonus, boolean unlockAbility) {
        if ( goldBonus;0) {
            p.hero.gold += goldBonus;
            System.out.println("Получено золото: +" + goldBonus + " (итого: " + p.hero.gold + ")");
        }
        if (unlockAbility) {
            // пример «сделки»: открыть повтор "Огненный шар" с меньшим КД
            var upgraded = new game.model.Ability(
                    "fireball_plus", "Огненный шар+",
                    "Усиленный огненный шар (меньше КД)",
                    1, game.model.DamageType.FIRE, 18, java.util.List.of(), true
            );
            boolean has = p.unlockedAbilities.contains(upgraded.id);
            if (!has) {
                p.unlockedAbilities.add(upgraded.id);
                p.hero.abilities.add(upgraded);
                System.out.println("Новый приём разблокирован: " + upgraded.name);
            } else {
                System.out.println("Торговцу нечего предложить нового.");
            }
        }
    }
}