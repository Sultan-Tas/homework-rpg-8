package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for Homework 8 — The Haunted Tower: Ascending the Floors.
 *
 * Build your heroes, floors, tower runner, and execute the climb here.
 */
public class Main {

    public static void main(String[] args) {
        Hero warrior = new Hero("Warrior", 200, 20, 15);
        Hero sniper = new Hero("Sniper", 140, 30, 8);

        TowerFloor floor1 = new EnemyFloor();
        TowerFloor floor2 = new RestFloor();
        TowerFloor floor3 = new PoisonedFloor();
        TowerFloor floor4 = new RestFloor();
        TowerFloor floor5 = new BossFloor();

        List<TowerFloor> floors = new ArrayList<TowerFloor>();
        floors.add(floor1);
        floors.add(floor2);
        floors.add(floor3);
        floors.add(floor4);
        floors.add(floor5);

        // TODO (student): Instantiate a tower runner and execute the tower climb
        // TODO (student): Track and print results (floors cleared, heroes surviving, tower status)
        // TODO (student): Demonstrate visible state transitions in the output
    }
}
