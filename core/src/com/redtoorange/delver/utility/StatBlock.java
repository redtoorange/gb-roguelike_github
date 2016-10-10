package com.redtoorange.delver.utility;

public class StatBlock {
    private AbilityScore strength, dexterity, constitution,
        wisdom, intelligence, charisma;

    public StatBlock(){
        strength        = new AbilityScore();
        dexterity       = new AbilityScore();
        constitution    = new AbilityScore();
        wisdom          = new AbilityScore();
        intelligence    = new AbilityScore();
        charisma        = new AbilityScore();
    }

    public StatBlock(int str, int dex, int con, int wis, int intel, int cha){
        strength        = new AbilityScore(str);
        dexterity       = new AbilityScore(dex);
        constitution    = new AbilityScore(con);
        wisdom          = new AbilityScore(wis);
        intelligence    = new AbilityScore(intel);
        charisma        = new AbilityScore(cha);
    }

    public int getBonus(AbilityScore.Type type){
        return getAbilityScore(type).getBonus();
    }

    public AbilityScore getAbilityScore(AbilityScore.Type type){
        switch(type){
            case STR:
                return strength;
            case DEX:
                return dexterity;
            case CON:
                return constitution;
            case WIS:
                return wisdom;
            case INT:
                return intelligence;
            case CHA:
                return charisma;
        }

        return null;
    }
}
