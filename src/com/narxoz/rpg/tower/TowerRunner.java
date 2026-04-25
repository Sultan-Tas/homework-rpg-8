package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.List;

public class TowerRunner {
    private List<TowerFloor> floors;
    private List<Hero> party;
    private int floorsCleared;
    private boolean towerCompleted;

    public TowerRunner(List<Hero> party, List<TowerFloor> floors) {
        this.party = party;
        this.floors = floors;
        this.floorsCleared = 0;
        this.towerCompleted = false;
    }

    //climbing the tower through loop
    public void climbTower() {
        if(party.isEmpty()) {
            throw new IllegalStateException("Party is empty");
        }
        System.out.println("=== TOWER CLIMB BEGINS ===");
        System.out.println("Party size: " + party.size());

        for (int i = 0; i < floors.size(); i++) {
            TowerFloor currentFloor = floors.get(i);

            // Explore the floor (call the template method)
            FloorResult result = currentFloor.explore(party);

            if (result.isCleared()) {
                //if party succeeded
                floorsCleared++;
                System.out.println("Floor " + (i + 1) + " cleared!");
            } else {
                System.out.println("Party defeated on floor " + (i + 1));
                break; // Stop climbing if party loses
            }

            // Check if any heroes are still alive
            if (!isAnyHeroAlive()) {
                System.out.println("All heroes have fallen!");
                break;
            }
        }

        // Check if we cleared all floors
        if (floorsCleared == floors.size()) {
            towerCompleted = true;
        }

        printFinalResults();
    }

    //check if any hero is alive
    private boolean isAnyHeroAlive() {
        for (Hero h : party) {
            if (h.isAlive()) {
                return true;
            }
        }
        return false;
    }

    //output the ultimate result
    private void printFinalResults() {
        System.out.println("\n=== TOWER CLIMB COMPLETE ===");
        System.out.println("Floors cleared: " + floorsCleared + "/" + floors.size());

        // Count surviving heroes
        int survivingHeroes = 0;
        for (Hero h : party) {
            if (h.isAlive()) {
                survivingHeroes++;
            }
        }
        System.out.println("Heroes surviving: " + survivingHeroes + "/" + party.size());

        // Tower status
        if (towerCompleted) {
            System.out.println("Tower status: CONQUERED!");
        } else {
            System.out.println("Tower status: Unfinished");
        }
    }

    // Getters for tracking results
    public int getFloorsCleared() {
        return floorsCleared;
    }

    public boolean isTowerCompleted() {
        return towerCompleted;
    }
}