package com.narxoz.rpg.combatant.behaviors;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;

public interface CombatBehavior {
    void onMonsterAttack(Monster attacker, Hero target);
}
