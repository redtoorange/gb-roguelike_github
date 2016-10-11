package com.redtoorange.delver.entities.inventory.types;

import com.redtoorange.delver.entities.Character;

public interface Equipable {
    void equip( Character c );
    void unequip( Character c );
}
