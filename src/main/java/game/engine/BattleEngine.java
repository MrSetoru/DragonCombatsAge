package game.engine;

import game.model.*;
import game.util.Rng;

import java.lang.Character;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static game.engine.ActionResolver.endTurn;
import static game.engine.ActionResolver.useAbility;
import static game.engine.Logger.*;

public class BattleEngine {
    public static void run(PlayerProfile profile, Scanner in) {
        Character player = profile.hero;
        Character enemy = createEnemy(profile);
        log("Противник: " + enemy.name + " HP " + enemy.stats.hp);
        hr();

        while (player.isAlive() &amp;&amp; enemy.isAlive()) {
            // Ход игрока
            player.tickCooldowns();
            enemy.tickCooldowns();
            printState(player, enemy);

            Ability chosen = chooseAbility(player, in);
            String res = useAbility(player, enemy, chosen);
            log(res);

            if (!enemy.isAlive()) break;

            // Ход противника (простой ИИ)
            Ability enemyAbility = chooseEnemyAbility(enemy, player);
            log(useAbility(enemy, player, enemyAbility));

            // Конец раунда: тики эффектов
            String ep = endTurn(player);
            String ee = endTurn(enemy);
            if (!ep.isEmpty()) log(ep);
            if (!ee.isEmpty()) log(ee);
            hr();
        }

        if (player.isAlive()) {
            log("Победа! +10 золота");
            profile.hero.gold += 10;
        } else {
            log("Поражение. Восстановление до половины здоровья.");
        }
        // восстановление после боя
        player.stats.hp = Math.max(player.stats.hp, Math.min(player.stats.maxHp, player.stats.maxHp / 2));
        // очистка временных эффектов и состояний
        player.effects.clear();
        player.shield = 0;
        player.guarding = false;
    }

    private static void printState(Character player, Character enemy) {
        log(player.name + " HP " + player.stats.hp + "/" + player.stats.maxHp + " | Щит " + player.shield);
        log(enemy.name + " HP " + enemy.stats.hp + "/" + enemy.stats.maxHp + " | Щит " + enemy.shield);
        String pfx = player.effects.isEmpty() ? "нет" : String.join(", ", player.effects.stream().map(Effect::name).toList());
        String efx = enemy.effects.isEmpty() ? "нет" : String.join(", ", enemy.effects.stream().map(Effect::name).toList());
        log("Эффекты игрока: " + pfx);
        log("Эффекты врага: " + efx);
        for (int i = 0; i &lt; player.abilities.size(); i++) {
            log((i + 1) + ") " + player.abilities.get(i).line());
        }
    }

    private static Ability chooseAbility(Character player, Scanner in) {
        while (true) {
            System.out.print("Выберите приём (1-" + player.abilities.size() + "): ");
            String s = in.nextLine().trim();
            try {
                int idx = Integer.parseInt(s) - 1;
                if (idx &gt;= 0 &amp;&amp; idx &lt; player.abilities.size()) return player.abilities.get(idx);
            } catch (Exception ignored) {}
            log("Некорректный выбор");
        }
    }

    private static Ability chooseEnemyAbility(Character enemy, Character player) {
        // приоритет: если немота доступна и у игрока нет немоты — наложить, если кулдаун 0
        List&lt;ability&gt; usable = new ArrayList&lt;&gt;();
        for (Ability a : enemy.abilities) if (a.cooldown == 0) usable.add(a);
        if (usable.isEmpty()) return enemy.abilities.get(0);
        // простая эвристика
        for (Ability a : usable) if (a.id.equals("silence") &amp;&amp; !player.hasEffect(EffectType.SILENCE)) return a;
        for (Ability a : usable) if (a.id.equals("bleed") &amp;&amp; !player.hasEffect(EffectType.BLEED)) return a;
        for (Ability a : usable) if (a.id.equals("poison_dart") &amp;&amp; !player.hasEffect(EffectType.POISON)) return a;
        for (Ability a : usable) if (a.id.equals("fireball")) return a;
        return usable.get(Rng.range(0, usable.size() - 1));
    }

    private static Character createEnemy(PlayerProfile profile) {
        Character e = new Character("Бандит", new Stats(90, 10, 9, 5, 4));
        // копия базового набора, но можно урезать
        e.abilities.addAll(new ArrayList&lt;&gt;(AbilityFactory.basicSet()));
        return e;
    }
}