package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.behaviors.DefaultMonsterBehavior;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.DefaultState;
import com.narxoz.rpg.state.StateFactory;
import com.narxoz.rpg.state.StateType;
import com.narxoz.rpg.state.StunnedState;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class EnemyFloor extends TowerFloor {
    private List<Monster> enemies;
    Random rand = new Random();

    @Override
    protected void setup(List<Hero> party) {
        Hero stunTarget = party.get(rand.nextInt(0, party.size())); //random stun of a party member
        stunTarget.setState(StateFactory.createHeroState(StateType.STUNNED));
        System.out.println(stunTarget.getName() + "was stunned");
        enemies = new ArrayList<>();
        enemies.add(new Monster("Zombie Brute", 200, 3));
        enemies.add(new Monster("Skeleton", 50, 15));
        enemies.add(new Monster("Sans", 20, 20));
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        return CombatEngine.executeCombat(
                party,
                enemies,
                new DefaultMonsterBehavior(),
                this
        );
    }

    @Override
    protected void cleanup(List<Hero> party) {
        for(Hero h : party){
            h.setState(StateFactory.createHeroState(StateType.DEFAULT));
        }
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Loot:");
        System.out.println("You received 67g");
        System.out.println("You got [Skeleton bones]");
        System.out.println("You got [Goblin meat]");
    }

    @Override
    protected String getFloorName() {
        return "Base Enemy floor";
    }
}
