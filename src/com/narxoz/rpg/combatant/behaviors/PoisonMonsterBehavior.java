package com.narxoz.rpg.combatant.behaviors;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.state.StateFactory;

public class PoisonMonsterBehavior implements CombatBehavior{
    @Override
    public void onMonsterAttack(Monster attacker, Hero target) {
        //intentional that monsters renew poisoned effect. It does not stack, but gets renewed after each hit
        target.setState(StateFactory.createPoisonedState(2));
    }
}
