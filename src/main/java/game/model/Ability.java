package game.model;

import java.util.List;

public class Ability {
    public final String id;
    public final String name;
    public final String description;
    public final int cooldownMax;
    public int cooldown;
    public final DamageType damageType;
    public final int baseDamage; // 0 для чисто-баффов
    public final List<effect> applyOnHit; // копируются при применении
    public final boolean requiresNoSilence; // если true — не работает под немотой

    public Ability(String id, String name, String description,
                   int cooldown, DamageType damageType, int baseDamage,
                   List&lt;

    effect&gt;applyOnHit,
    boolean requiresNoSilence)

    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cooldownMax = cooldown;
        this.cooldown = 0;
        this.damageType = damageType;
        this.baseDamage = baseDamage;
        this.applyOnHit = applyOnHit;
        this.requiresNoSilence = requiresNoSilence;
    }

    public String line() {
        return name + " [cd:" + cooldown + "/" + cooldownMax + "] — " + description;
    }
}