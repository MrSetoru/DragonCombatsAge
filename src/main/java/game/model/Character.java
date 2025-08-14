package game.model;

import java.util.ArrayList;
import java.util.List;

public class Character {
    public String name;
    public Stats stats;
    public List<ability> abilities = new ArrayList<>();
    public List<effect> effects = new ArrayList<>();
    public int shield; // поглощение урона
    public boolean guarding; // снижает урон
    public int gold = 0;

    public Character(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    public boolean isAlive() {
        return stats.hp & gt;
        0;
    }

    public void tickCooldowns() {
        for (Ability a : abilities) if ( a.cooldown & gt;
        0)a.cooldown--;
    }

    public boolean hasEffect(EffectType t) {
        return effects.stream().anyMatch(e - & gt; e.type == t & amp;&amp;
        e.turns & gt;
        0);
    }
}