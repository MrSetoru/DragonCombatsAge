package game.model;

public class AbilityFactory {
    public static java.util.List<Ability> basicSet() {
        return java.util.List.of(
                new Ability("strike", "Удар", "Физ. урон", 0, DamageType.PHYSICAL, 12, java.util.List.of(), true),
                new Ability("guard", "Стойка", "Встать в защиту (Guard), снижает урон на 40% 1 ход", 2, DamageType.PHYSICAL, 0,
                        java.util.List.of(new Effect(EffectType.GUARD, 1, 40)), false),
                new Ability("bleed", "Кровопускание", "Наносит урон и накладывает Кровотечение (5 урона/ход, 3 хода)", 2,
                        DamageType.PHYSICAL, 8, java.util.List.of(new Effect(EffectType.BLEED, 3, 5)), true),
                new Ability("fireball", "Огненный шар", "Маг. урон огнём", 2, DamageType.FIRE, 16, java.util.List.of(), true),
                new Ability("poison_dart", "Отравленная игла", "Малый урон и Яд (4/ход, 4 хода)", 2,
                        DamageType.POISON, 6, java.util.List.of(new Effect(EffectType.POISON, 4, 4)), true),
                new Ability("silence", "Немота", "Накладывает Немоту на 1 ход (запрет способностей с флагом)", 3,
                        DamageType.ARCANE, 0, java.util.List.of(new Effect(EffectType.SILENCE, 1, 1)), true),
                new Ability("shield", "Магический щит", "Даёт Щит 20 на 2 хода", 3,
                        DamageType.ARCANE, 0, java.util.List.of(new Effect(EffectType.SHIELD, 2, 20)), true),
                new Ability("disorient", "Дезориентация", "Снижает точность врага на 25% (1 ход)", 2,
                        DamageType.ARCANE, 0, java.util.List.of(new Effect(EffectType.DISORIENT, 1, 25)), true)
        );
    }
}