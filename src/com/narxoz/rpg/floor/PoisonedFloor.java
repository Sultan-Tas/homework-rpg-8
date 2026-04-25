package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.DefaultState;
import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.PoisonedState;

import java.util.ArrayList;
import java.util.List;

public class PoisonedFloor extends TowerFloor {
    private List<Monster> enemies;

    @Override
    protected void setup(List<Hero> party) {
        for (Hero hero : party) {
            hero.setState(new PoisonedState(3));
        }
        enemies = new ArrayList<>();
        enemies.add(new Monster("Slug", 50, 8));
        enemies.add(new Monster("Slush", 100, 5));
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int damageTaken = 0;
        int round = 0;
        boolean partyIsAlive = false;
        boolean enemyIsAlive = true;
        for (Hero h : party) {
            if(h.isAlive()){
                partyIsAlive = true;
                break;
            }
        }
        while(partyIsAlive & enemyIsAlive){
            round++;
            System.out.println("\nRound " + round + ":");
            for(Hero h : party){
                if(h.isAlive()){
                    Monster target = enemies.get(0);    //get hero's target
                    HeroState currState = h.getState(); //variable for hero's state
                    currState.onTurnStart(h);           //call onTurnStart() method

                    //check if her can act
                    if(!currState.canAct()){
                        System.out.println(h.getName() + " can not act...");
                        continue;
                    }

                    int dmg = currState.modifyOutgoingDamage(h.getAttackPower()); //calculate outgoing dmg
                    System.out.println("\t►[" + target.getName() + "] received " + dmg + " from " + h.getName()); //log message
                    target.takeDamage(dmg);     //monster takes damage
                    currState.onTurnEnd(h);     //call onTurnEnd() method
                    if(!target.isAlive()){
                        System.out.println("\t[" + target.getName() + "] is dead.");
                        enemies.remove(target);
                    } // check if target is still alive
                }
                //check if enemy is still alive
                enemyIsAlive = false;
                for(Monster m : enemies){
                    if(m.isAlive()){
                        enemyIsAlive = true;
                        break;
                    }
                }
            }
            for(Monster m : enemies){
                if(m.isAlive()){
                    Hero target = party.get(0);
                    //get first alive hero
                    for(Hero h : party){
                        if(h.isAlive()){
                            target = h;
                            break;
                        }
                    }
                    int incomingDMG = target.getState().modifyIncomingDamage(m.getAttackPower())- target.getDefense(); //calculate incoming damage
                    damageTaken += incomingDMG;         //increment taken damage
                    target.takeDamage(incomingDMG);
                    target.setState(new PoisonedState(2));//apply damage to the hero
                    System.out.println("\t►" + target.getName() + " received " + incomingDMG + " from [" + m.getName() + "]"); //log message
                    if(!target.isAlive()){
                        System.out.println("\t" + target.getName() + " fainted");
                    } //log if hero died
                }
                //check if party is still alive
                partyIsAlive = false;
                for(Hero h : party){
                    if(h.isAlive()){
                        partyIsAlive = true;
                        break;
                    }
                }
            }
        }

        if(partyIsAlive){
            return new FloorResult(true, damageTaken, "Heroes conquered " + getFloorName());
        }
        else{
            return new FloorResult(false, damageTaken, "The final stop was " + getFloorName());
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        for(Hero h : party){
            h.setState(DefaultState.getInstance());
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
