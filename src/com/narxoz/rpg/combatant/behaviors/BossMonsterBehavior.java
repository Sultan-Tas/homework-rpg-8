package com.narxoz.rpg.combatant.behaviors;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.StateFactory;
import com.narxoz.rpg.state.StateType;
import com.narxoz.rpg.state.StunnedState;

import java.util.Random;

public class BossMonsterBehavior implements CombatBehavior{
    Random rand = new Random();

    @Override
    public void onMonsterAttack(Monster attacker, Hero target) {
        if(rand.nextDouble() < 0.2){
            target.setState(StateFactory.createHeroState(StateType.STUNNED));
            System.out.println("\t‼ ►" + target.getName() + " was stunned!");
        }
    }
}
