package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.combatant.behaviors.PoisonMonsterBehavior;
import com.narxoz.rpg.state.DefaultState;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.state.StateFactory;
import com.narxoz.rpg.state.StateType;

import java.util.ArrayList;
import java.util.List;

public class PoisonedFloor extends TowerFloor {
    private List<Monster> enemies;

    @Override
    protected void setup(List<Hero> party) {
        for (Hero hero : party) {
            hero.setState(StateFactory.createPoisonedState(3));
        }
        enemies = new ArrayList<>();
        enemies.add(new Monster("Slug", 50, 8));
        enemies.add(new Monster("Slush", 100, 5));
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        return CombatEngine.executeCombat(
                party,
                enemies,
                new PoisonMonsterBehavior(),
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
        System.out.println("You earned 35g");
        System.out.println("You got [Poison Stone]");
    }

    @Override
    protected String getFloorName() {
        return "Poisonous Sewer";
    }
}
