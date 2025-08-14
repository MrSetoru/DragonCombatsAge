package game.model;

import game.engine.AbilityFactory;

import java.util.ArrayList;
import java.util.List;

public class PlayerProfile {
    public String playerName;
    public Character hero;
    public int reputation = 0; // выборы в диалогах меняют это
    public List<string> unlockedAbilities = new ArrayList<>();

    public static PlayerProfile createDefault(String name) {
        PlayerProfile p = new PlayerProfile();
        p.playerName = name;
        p.hero = new Character(name, new Stats(100, 12, 8, 6, 5));
        // 8 приёмов
        p.hero.abilities.addAll(AbilityFactory.basicSet());
        p.unlockedAbilities.addAll(p.hero.abilities.stream().map(a - & gt; a.id).toList());
        p.hero.gold = 20;
        return p;
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Игрок: ").append(playerName).append("\n");
        sb.append("Репутация: ").append(reputation).append("  Золото: ").append(hero.gold).append("\n");
        sb.append("Персонаж: ").append(hero.name).append(" HP ").append(hero.stats.hp)
                .append("/").append(hero.stats.maxHp).append("\n");
        sb.append("Статы: POW ").append(hero.stats.power)
                .append(" MAS ").append(hero.stats.mastery)
                .append(" DEF ").append(hero.stats.defense)
                .append(" RES ").append(hero.stats.resistance).append("\n");
        sb.append("Эффекты: ");
        if (hero.effects.isEmpty()) sb.append("нет\n");
        else {
            hero.effects.forEach(e - & gt; sb.append(e.name()).append(" "));
            sb.append("\n");
        }
        sb.append("Приёмы:\n");
        for (int i = 0; i & lt; hero.abilities.size() ;
        i++){
            sb.append("  ").append(i + 1).append(") ").append(hero.abilities.get(i).line()).append("\n");
        }
        return sb.toString();
    }
}