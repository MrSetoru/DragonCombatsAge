package game.model;

public class Stats {
    public int maxHp;
    public int hp;
    public int power; // влияет на физ. урон
    public int mastery; // влияет на маг. урон/эффекты
    public int defense; // снижает физ. урон
    public int resistance;// снижает маг. урон

    public Stats(int maxHp, int power, int mastery, int defense, int resistance) {
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.power = power;
        this.mastery = mastery;
        this.defense = defense;
        this.resistance = resistance;
    }

    public Stats copy() {
        Stats s = new Stats(maxHp, power, mastery, defense, resistance);
        s.hp = hp;
        return s;
    }
}