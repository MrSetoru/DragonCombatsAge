package game.dialog;

import game.io.SaveLoad;
import game.model.PlayerProfile;
import game.model.Ability;
import game.model.DamageType;

import java.util.List;
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
                break;
            }
            case "2": {
                profile.reputation -= 1;
                System.out.println("Тонкая грань между напором и хамством. Репутация -1.");
                // 50% скидка: либо бонус золота, либо разблокировка способности
                if (Math.random() < 0.5) {
                    reward(profile, 10, false);
                } else {
                    reward(profile, 0, true);
                }
                break;
            }
            case "3": {
                System.out.println("Вы уходите, ничего не происходит.");
                break;
            }
            default: {
                System.out.println("Некорректный выбор. Диалог окончен.");
                break;
            }
        }

        System.out.println("Сохранить прогресс? (y/n)");
        String s = in.nextLine().trim().toLowerCase();
        if (s.startsWith("y")) {
            SaveLoad.save(profile, "save.json");
            System.out.println("Сохранено.");
        }
    }

    /**
     * Метод награды игрока.
     *
     * @param p             профиль игрока
     * @param goldBonus     количество золота для награды
     * @param unlockAbility разблокировать способность или нет
     */
    private static void reward(PlayerProfile p, int goldBonus, boolean unlockAbility) {
        if (goldBonus > 0) {
            p.hero.gold += goldBonus;
            System.out.println("Получено золото: +" + goldBonus + " (итого: " + p.hero.gold + ")");
        }
        if (unlockAbility) {
            // Пример «сделки»: открыть усиленный "Огненный шар" с меньшим КД
            Ability upgraded = new Ability(
                    "fireball_plus", "Огненный шар+",
                    "Усиленный огненный шар (меньше КД)",
                    1, DamageType.FIRE, 18, List.of(), true
            );
            boolean has = p.unlockedAbilities.contains(upgraded.getId());
            if (!has) {
                p.unlockedAbilities.add(upgraded.getId());
                p.hero.abilities.add(upgraded);
                System.out.println("Новый приём разблокирован: " + upgraded.getName());
            } else {
                System.out.println("Торговцу нечего предложить нового.");
            }
        }
    }
}