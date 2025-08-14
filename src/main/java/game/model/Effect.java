package game.model;

public class Effect {
    public final EffectType type;
    public int turns;
    public int potency; // универсальная сила эффекта (урон за тик, штраф, сила щита и т.п.)
    public DamageType tag; // опционально для уязвимости/усиления (не используется в этом минимуме)

    public Effect(EffectType type, int turns, int potency) {
        this.type = type;
        this.turns = turns;
        this.potency = potency;
    }

    public String name() {
        return type.name() + "(" + potency + ", " + turns + ")";
    }
}