package com.redtoorange.delver.entities.inventory;

import com.redtoorange.delver.entities.Character;

public interface Equipable {
    void equip( Character c );
    void unequip( Character c );
}
