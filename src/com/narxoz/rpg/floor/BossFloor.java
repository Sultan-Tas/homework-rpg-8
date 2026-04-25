package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.behaviors.BossMonsterBehavior;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.DefaultState;
import com.narxoz.rpg.state.StateFactory;
import com.narxoz.rpg.state.StateType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossFloor extends TowerFloor {
    private List<Monster> enemies;
    Random rand = new Random();

    @Override
    protected void setup(List<Hero> party) {
        enemies = new ArrayList<>();
        enemies.add(new Monster("Monster King", 400, 30));
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        return CombatEngine.executeCombat(
                party,
                enemies,
                new BossMonsterBehavior(),
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
        System.out.println("You received 160g");
        System.out.println("You got [Magical Jewels]");
    }

    @Override
    protected String getFloorName() {
        return "Boss Room floor";
    }
}
