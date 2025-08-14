package game.model;

import java.util.ArrayList;
import java.util.List;

public class Character {
    public String name;
    public Stats stats;
    public List<Ability> abilities = new ArrayList<>();
    public List<Effect> effects = new ArrayList<>();
    public int shield; // поглощение урона
    public boolean guarding; // снижает урон
    public int gold = 0;

    public Character(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    public boolean isAlive() {
        return stats.hp > 0;
    }

    public void tickCooldowns() {
        for (Ability a : abilities) {
            if (a.cooldown > 0) {
                a.cooldown--;
            }
        }
    }

    public boolean hasEffect(EffectType t) {
        return effects.stream().anyMatch(e -> e.type == t && e.turns > 0);
    }
}