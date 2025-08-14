package game.engine;

import game.model.*;
import game.model.Character;

import static game.engine.Logger.log;

public class ActionResolver {
    public static String useAbility(Character actor, Character target, Ability a) {
        if (a.requiresNoSilence && actor.hasEffect(EffectType.SILENCE))
            return actor.name + " пытается использовать " + a.name + ", но находится под Немотой!";
        if (a.cooldown > 0)
            return a.name + " на перезарядке (" + a.cooldown + ")";

        a.cooldown = a.cooldownMax;

        // Некоторым умениям не нужна атака/точность
        boolean requiresHitCheck = a.baseDamage > 0 || a.applyOnHit.stream().anyMatch(e -> e.type == EffectType.BLEED || e.type == EffectType.POISON);
        if (requiresHitCheck) {
            int baseHit = 90;
            if (target.hasEffect(EffectType.DISORIENT)) baseHit -= 25;
            if (!game.util.Rng.chance(baseHit)) return actor.name + " использует " + a.name + ", но промахивается!";
        }

        StringBuilder out = new StringBuilder();
        out.append(actor.name).append(" использует ").append(a.name).append(". ");

        // Применение щита/гарда до урона
        for (Effect e : a.applyOnHit) {
            if (e.type == EffectType.SHIELD) {
                actor.shield += e.potency;
                actor.effects.add(new Effect(EffectType.SHIELD, e.turns, e.potency));
                out.append("Щит ").append(e.potency).append(" на ").append(e.turns).append(" хода. ");
            } else if (e.type == EffectType.GUARD) {
                actor.guarding = true;
                actor.effects.add(new Effect(EffectType.GUARD, e.turns, e.potency));
                out.append("Стойка: -").append(e.potency).append("% урона на ").append(e.turns).append(" ход. ");
            }
        }

        // Урон
        if (a.baseDamage > 0) {
            int dmg = computeDamage(actor, target, a);
            int dealt = dealDamage(target, dmg);
            out.append("Урон: ").append(dealt).append(". ");
        }

        // Наложение эффектов на цель
        for (Effect e : a.applyOnHit) {
            if (e.type == EffectType.BLEED || e.type == EffectType.POISON || e.type == EffectType.SILENCE || e.type == EffectType.DISORIENT) {
                target.effects.add(new Effect(e.type, e.turns, e.potency));
                out.append("Накладывает ").append(e.type.name()).append(" на ").append(e.turns).append(" ход. ");
            }
        }

        return out.toString();
    }

    private static int computeDamage(Character actor, Character target, Ability a) {
        int base = a.baseDamage;
        switch (a.damageType) {
            case PHYSICAL -> base += actor.stats.power;
            case FIRE, POISON, ARCANE -> base += actor.stats.mastery;
        }
        int mitigation;
        switch (a.damageType) {
            case PHYSICAL -> mitigation = target.stats.defense;
            default -> mitigation = target.stats.resistance;
        }
        int raw = Math.max(0, base - mitigation);
        // Guard
        if (target.guarding) raw = (int)Math.round(raw * 0.6);
        return Math.max(0, raw);
    }

    private static int dealDamage(Character target, int dmg) {
        int absorbed = Math.min(target.shield, dmg);
        target.shield -= absorbed;
        int hpLoss = dmg - absorbed;
        target.stats.hp = Math.max(0, target.stats.hp - hpLoss);
        return hpLoss;
    }

    public static String endTurn(Character c) {
        StringBuilder sb = new StringBuilder();
        // тики эффектов
        int bleed = c.effects.stream().filter(e -> e.type == EffectType.BLEED && e.turns > 0).mapToInt(e -> e.potency).sum();
        int poison = c.effects.stream().filter(e -> e.type == EffectType.POISON && e.turns > 0).mapToInt(e -> e.potency).sum();
        int dot = bleed + poison;
        if (dot > 0) {
            int taken = dealDamage(c, dot);
            sb.append(c.name).append(" страдает от эффектов: ").append(taken).append(" урона. ");
        }
        // уменьшить длительность, очистить закончившиеся, снять guard если закончился
        for (Effect e : c.effects) e.turns--;
        c.effects.removeIf(e -> e.turns <= 0);
        c.guarding = c.hasEffect(EffectType.GUARD);
        // тик кулдаунов происходит вне, в BattleEngine
        return sb.toString();
    }
}