package com.redtoorange.delver.utility;

import com.badlogic.gdx.math.MathUtils;

public class Dice {
    public static Dice D4 = new Dice(1, 4);
    public static Dice D6 = new Dice(1, 6);
    public static Dice D8 = new Dice(1, 8);
    public static Dice D10 = new Dice(1, 10);
    public static Dice D12 = new Dice(1, 12);
    public static Dice D20 = new Dice(1, 20);

    private int sides;
    private int count;

    public int maxDamage(){
        return sides * count;
    }

    public Dice(int count, int sides){
        this.sides = sides;
        this.count = count;
    }

    public int roll(){
        return MathUtils.random(count, count * sides);
    }
}




