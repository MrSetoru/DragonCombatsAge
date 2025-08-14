package game.model;

import java.util.List;

public class Ability {
    public final String id;
    public final String name;
    public final String description;
    public final int cooldownMax;
    public int cooldown;
    public final DamageType damageType;
    public final int baseDamage; // 0 для баффов, не наносящих урон
    public final List<Effect> applyOnHit; // Копируются при применении
    public final boolean requiresNoSilence; // true — не работает под немотой

    public Ability(String id, String name, String description,
                   int cooldown, DamageType damageType, int baseDamage,
                   List<Effect> applyOnHit, boolean requiresNoSilence) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cooldownMax = cooldown;
        this.cooldown = 0; // При создании способности кулдаун равен 0
        this.damageType = damageType;
        this.baseDamage = baseDamage;
        this.applyOnHit = applyOnHit;
        this.requiresNoSilence = requiresNoSilence;
    }

    public String line() {
        return name + " [cd:" + cooldown + "/" + cooldownMax + "] — " + description;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}