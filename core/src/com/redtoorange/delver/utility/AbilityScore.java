package com.redtoorange.delver.utility;

public class AbilityScore {
    private int mod;
    private int value;

    public AbilityScore(){
        this(10, 0);
    }

    public AbilityScore(int value){
        this(value, 0);
    }

    public AbilityScore(int value, int startingMod){
        this.value = value;
        mod = startingMod;
    }

    public int getValue(){
        return value + mod;
    }

    public int getBonus(){
        return (getValue() - 10) / 2;
    }

    public void applyAbilityMod( int mod ){
        this.mod += mod;
    }

    public void clearAllMods(){
        mod = 0;
    }

    public void changeValue(int changeAmount){
        value += changeAmount;
    }

    public void setValue(int value){
        this.value = value;
    }

    public enum Type{
        STR, DEX, CON, WIS, INT, CHA
    }
}
