package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

public class RestFloor extends TowerFloor {
    @Override
    protected void setup(List<Hero> party) {
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        for(Hero hero : party) {
            hero.heal((int) (hero.getMaxHp()*0.2));
            System.out.println(hero.getName() + ": " + hero.getHp() + "/" + hero.getMaxHp());
        }
        return new FloorResult(true, 0, "Heroes rest and heal 20% of their hp");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("There was no loot, but the party patched their wounds (everyone healed 20%hp)");
    }

    @Override
    protected String getFloorName() {
        return "Rest & Heal floor";
    }
}
