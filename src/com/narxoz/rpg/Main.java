package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.*;
import com.narxoz.rpg.tower.TowerRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for Homework 8 — The Haunted Tower: Ascending the Floors.
 *
 * Build your heroes, floors, tower runner, and execute the climb here.
 */
public class Main {

    public static void main(String[] args) {
        Hero warrior = new Hero("Warrior", 150, 20, 10);
        Hero slayer = new Hero("Slayer", 130, 25, 8);
        Hero sniper = new Hero("Sniper", 100, 30, 5);
        List<Hero> heroes = new ArrayList<>();
        heroes.add(warrior);
        heroes.add(slayer);
        heroes.add(sniper);

        List<TowerFloor> floors = new ArrayList<TowerFloor>();
        floors.add(new EnemyFloor());      //floor 1
        floors.add(new RestFloor());       //floor 2
        floors.add(new PoisonedFloor());   //floor 3
        floors.add(new RestFloor());       //floor 4
        floors.add(new BossFloor());       //floor 5


        TowerRunner runner = new TowerRunner(heroes, floors);
        runner.climbTower();
    }
}
